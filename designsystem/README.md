### DroidMorning Design System (KMP)

이 문서는 `:designsystem` 모듈의 **현 상태를 평가**하고, **최적화/정리 포인트를 우선순위로 정리**한 내부 가이드입니다.

---

### 1) 목표/범위(권장)

- **목표**: 앱 전반 UI의 일관성(색/타이포/간격/모션)과 재사용성(컴포넌트)을 높이고, 변경 비용을 낮춘다.
- **권장 범위**
  - **Foundation(토큰)**: 색/타이포/간격/shape/모션/아이콘 규칙
  - **Primitive components**: Button, Chip, Checkbox, Radio, Switch, TextField/TextArea, BottomSheet, Dialog 등
  - **패턴/컴포넌트**: Loading/Error/Success 등 재사용 가능한 공통 패턴
- **비권장(또는 분리 권장)**: `QuestionCard`, `TestHistoryCard` 같은 **특정 화면/도메인에 종속된 “feature UI”**는 `presentation/composeApp`로 분리하고, 디자인시스템에는 “카드/배지/버튼” 같은 재사용 primitives만 남기는 것을 추천합니다.

---

### 2) 현재 상태 평가(요약)

#### 강점(좋은 점)
- **KMP + Compose Multiplatform 기반**으로 DS를 공용 모듈화한 점이 좋습니다.
- `AppTheme`에서 **Light/Dark ColorScheme**을 명확히 구성했고, `Font.kt`/`Type.kt`로 **타이포 시스템**을 갖춘 점이 좋습니다.
- `compose.resources` 설정으로 **멀티플랫폼 리소스 관리**(폰트/드로어블) 방향이 올바릅니다.
- 여러 컴포넌트에 `@Preview`가 존재해 **개발/회귀 확인 속도**를 높일 수 있습니다.

#### 리스크/개선 포인트(핵심)
- **네이밍/구조 일관성 부족**
  - 파일명/함수명이 불일치하거나, `App*` vs `DroidMorning*` 네이밍이 혼재되어 유지보수 비용이 상승합니다.
  - `Dimen`/`Shape`는 객체 기반인데, 일부 컴포넌트는 과거 네이밍(`Dimens`, `AppShapes`)의 흔적이 있어 컴파일/리팩터링 비용이 발생합니다.
- **레이어 경계(모듈 책임) 혼재**
  - 도메인/화면 종속 컴포넌트가 DS에 함께 존재하면, DS 변경이 곧 “기능 UI” 변경으로 전파됩니다.
  - 예: 카테고리(`domain`)와 색상(`designsystem`)의 매핑은 DS가 아니라 **UI 레이어(composeApp/presentation)**에서 책임지는 편이 일반적으로 더 안전합니다.
- **디자인 토큰 사용 방식이 혼재**
  - 일부 컴포넌트는 `MaterialTheme.colorScheme`를 쓰고, 일부는 직접 색상 상수(`Primary`, `Border` 등)를 사용합니다.
  - alpha 처리(예: `Error.copy(alpha = 0.15f)`)가 컴포넌트마다 제각각이라, 일관된 “container 톤”이 유지되기 어렵습니다.
- **프리뷰/샘플 데이터가 산발적**
  - `TestPreview` 같은 임시 프리뷰는 “샘플/카탈로그” 관점에서 정리 대상입니다.

---

### 3) 최적화 제안(우선순위)

#### P0 (즉시) — 안정성/일관성
- **네이밍 단일화**
  - 권장: 컴포넌트는 `App*`로 통일(예: `AppCheckbox`, `AppRadioButton`, `AppTextArea`) 또는 `DroidMorning*`로 통일(둘 중 하나만).
  - **파일명 = public Composable명**으로 맞추기 (검색/리팩터링 비용 절감).
- **토큰 접근 방식 통일**
  - `Dimen.*`, `Shape.*`는 한 방식으로 고정하고(현재 객체 기반), 기존 흔적(`Dimens`, `AppShapes`)은 전부 제거.
- **컴포넌트 API 규칙 고정**
  - 파라미터 순서 권장: `value/state` → `onEvent` → `modifier` → `enabled` → `style/options`
  - 모든 컴포넌트에 `modifier: Modifier = Modifier` 제공.

#### P1 (다음) — 토큰/테마 품질
- **Semantic color 토큰 확장**
  - 이미 `AppTheme`에 `errorContainer/onErrorContainer`가 있으니, 컴포넌트에서 “직접 alpha” 대신
    - `MaterialTheme.colorScheme.errorContainer`
    - `MaterialTheme.colorScheme.onErrorContainer`
    같은 **의미 기반** 사용을 늘리기.
- **상태별 컬러(pressed/disabled/selected) 정책**
  - Button/Chip/Input 등에서 disabled/pressed alpha를 일관된 규칙으로 정의(문서화).
- **컴포넌트별 Defaults 제공**
  - 예: `AppTextAreaDefaults`, `AppSearchBarDefaults`, `AppDialogDefaults`처럼 색/shape/padding을 한곳에 모아 변경 지점을 줄이기.

#### P2 (중기) — 접근성/테스트/카탈로그
- **접근성(semantics) 정책**
  - Checkbox/Radio/Switch는 contentDescription을 넣는 패턴이 좋아요. 이를 전 컴포넌트로 확장하고, 기본값 규칙을 문서화.
- **Preview 카탈로그화**
  - `preview` 패키지에 “샘플 데이터 + 프리뷰 스위트”를 모으고, 임시 프리뷰는 제거/정리.
- **스냅샷 테스트/린트**
  - Android는 Paparazzi/Roborazzi 등, iOS/desktop은 가능한 범위에서 렌더링 회귀 체크를 검토.

---

### 4) 모듈 경계 설계(추천)

#### 권장 의존성 방향
- `domain` → (순수 모델/유즈케이스)
- `designsystem` → (UI 토큰/primitive components) **domain에 의존하지 않음**
- `presentation/composeApp` → domain + designsystem을 조합해서 “화면 UI” 구성

#### 카테고리 같은 “도메인 값 → 색상” 매핑 위치
- 추천: `presentation`(또는 `composeApp`)에 `Category -> CategoryBadgeColors` 같은 매퍼를 둔다.
- `designsystem`은 `CategoryBadge(text, colors)`처럼 **표현만 담당**한다.

---

### 5) 컴포넌트 작성 규칙(권장 컨벤션)

- **이름**
  - Public 컴포넌트: `AppXxx` (혹은 `DroidMorningXxx`)로 통일
  - 파일명: `AppXxx.kt`
- **디자인 토큰**
  - spacing/size: `Dimen.*`
  - shape: `Shape.*` 또는 `MaterialTheme.shapes.*`(둘 중 한 방식으로 통일)
  - 색: 가능하면 `MaterialTheme.colorScheme.*` 우선, 부족하면 `theme/Color.kt`에 추가
- **프리뷰**
  - 최소 2종(기본/상태 변화: disabled, error, selected 등)
  - `AppTheme { ... }`로 감싸기

---

### 6) 정리 체크리스트(실행 플랜 예시)

- [ ] 컴포넌트 네이밍 통일(`App*` vs `DroidMorning*`) 결정
- [ ] 파일명/함수명 정합성 맞추기
- [ ] `Dimens`, `AppShapes` 등 레거시 네이밍 전부 제거
- [ ] feature 성격 컴포넌트(`QuestionCard` 등) 분리 여부 결정
- [ ] `Defaults` 객체 도입(버튼/입력/다이얼로그부터)
- [ ] Preview 카탈로그 정리(임시 프리뷰 제거, 샘플 데이터 통합)
