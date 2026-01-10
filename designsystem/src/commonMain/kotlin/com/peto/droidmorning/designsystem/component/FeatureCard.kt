package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Border
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.OnSurface
import com.peto.droidmorning.designsystem.theme.Shape
import com.peto.droidmorning.designsystem.theme.Surface
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FeatureCard(
    icon: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(Shape.featureItem)
                .background(Surface.copy(alpha = 0.6f))
                .border(
                    width = 1.dp,
                    color = Border.copy(alpha = 0.5f),
                    shape = Shape.featureItem,
                ).padding(Dimen.featureItemPadding),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.spacingMd),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurface.copy(alpha = 0.9f),
            )
        }
    }
}

@Preview
@Composable
private fun FeatureCardPreview() {
    AppTheme {
        FeatureCard(
            icon = "🙂",
            text = "Feature card",
        )
    }
}
