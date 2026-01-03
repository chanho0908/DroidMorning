#!/bin/bash

# Git hooks 설치 스크립트
# 이 스크립트는 프로젝트의 Git hooks를 설치합니다.

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
HOOKS_DIR="$PROJECT_ROOT/.git/hooks"

echo "🔧 Installing Git hooks..."
echo ""

# pre-push hook 생성
cat > "$HOOKS_DIR/pre-push" << 'EOF'
#!/bin/bash

# Pre-push hook to run CI tests locally before pushing
# This prevents pushing code that would fail CI

set -e  # Exit on first error

echo "🚀 Running pre-push checks..."
echo ""

# Check if SKIP_PRE_PUSH is set
if [ "$SKIP_PRE_PUSH" = "1" ]; then
    echo "⚠️  Skipping pre-push checks (SKIP_PRE_PUSH=1)"
    exit 0
fi

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Track overall success
FAILED=0

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📋 Step 1/3: Running ktlint check..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
if ./gradlew ktlintCheck --quiet; then
    echo -e "${GREEN}✅ ktlint check passed${NC}"
else
    echo -e "${RED}❌ ktlint check failed${NC}"
    FAILED=1
fi
echo ""

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "🧪 Step 2/3: Running unit tests..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
if ./gradlew testDebugUnitTest --quiet; then
    echo -e "${GREEN}✅ Unit tests passed${NC}"
else
    echo -e "${RED}❌ Unit tests failed${NC}"
    FAILED=1
fi
echo ""

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "🔨 Step 3/3: Building Android Debug..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
if ./gradlew :composeApp:assembleDebug --quiet; then
    echo -e "${GREEN}✅ Android Debug build passed${NC}"
else
    echo -e "${RED}❌ Android Debug build failed${NC}"
    FAILED=1
fi
echo ""

# Optional: iOS build (can be slow, so it's controlled by env variable)
if [ "$RUN_IOS_BUILD" = "1" ]; then
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "🍎 Optional: Building iOS Debug Framework..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    if ./gradlew :composeApp:linkDebugFrameworkIosArm64 --quiet; then
        echo -e "${GREEN}✅ iOS Debug build passed${NC}"
    else
        echo -e "${RED}❌ iOS Debug build failed${NC}"
        FAILED=1
    fi
    echo ""
fi

# Summary
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}✅ All pre-push checks passed! Proceeding with push...${NC}"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    exit 0
else
    echo -e "${RED}❌ Some checks failed. Push aborted.${NC}"
    echo ""
    echo "💡 Tips:"
    echo "  - Fix the failing checks and try again"
    echo "  - To skip these checks: SKIP_PRE_PUSH=1 git push"
    echo "  - To run iOS build: RUN_IOS_BUILD=1 git push"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    exit 1
fi
EOF

# 실행 권한 부여
chmod +x "$HOOKS_DIR/pre-push"

echo "✅ Git hooks installed successfully!"
echo ""
echo "📝 Installed hooks:"
echo "  - pre-push: Runs CI checks before pushing"
echo ""
echo "💡 Usage:"
echo "  Normal push:              git push"
echo "  Skip pre-push checks:     SKIP_PRE_PUSH=1 git push"
echo "  Run with iOS build:       RUN_IOS_BUILD=1 git push"
echo ""
