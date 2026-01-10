package com.peto.droidmorning.designsystem.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Border
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.Primary
import com.peto.droidmorning.designsystem.theme.Shape
import com.peto.droidmorning.designsystem.theme.Surface
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InteractiveCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateDpAsState(
        targetValue = if (isPressed) 0.98.dp else 1.dp,
    )

    Card(
        modifier =
            modifier
                .scale(scale.value)
                .clickable(
                    interactionSource = interactionSource,
                    onClick = onClick,
                ),
        shape = Shape.card,
        colors =
            CardDefaults.cardColors(
                containerColor = Surface,
            ),
        border =
            BorderStroke(
                width = 1.dp,
                color = if (isPressed) Primary.copy(alpha = 0.3f) else Border,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = Dimen.cardElevation,
            ),
    ) {
        Column(
            modifier = Modifier.padding(Dimen.cardPadding),
            content = content,
        )
    }
}

@Preview
@Composable
private fun InteractiveCardPreview() {
    AppTheme {
        InteractiveCard(onClick = {}) {
            Text("Hello World!")
        }
    }
}
