package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.MutedForeground
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MutedForeground,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(Dimen.touchTargetMin),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(Dimen.iconMd),
        )
    }
}

@Preview
@Composable
private fun AppIconButtonPreview() {
    AppTheme {
        IconButton(
            onClick = {},
            icon = Icons.Default.Favorite,
            contentDescription = "Favorite",
        )
    }
}
