package com.peto.droidmorning.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.peto.droidmorning.designsystem.generated.resources.DesignRes
import com.peto.droidmorning.designsystem.generated.resources.btn_google_login
import com.peto.droidmorning.designsystem.generated.resources.google_sign_in_button_description
import com.peto.droidmorning.designsystem.theme.AppTheme
import com.peto.droidmorning.designsystem.theme.Dimen
import com.peto.droidmorning.designsystem.theme.OnSurface
import com.peto.droidmorning.designsystem.theme.Shape
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier =
            modifier
                .fillMaxWidth()
                .height(Dimen.buttonHeightLg),
        shape = Shape.buttonPill,
        border = null,
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = OnSurface,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = OnSurface.copy(alpha = 0.5f),
            ),
    ) {
        Image(
            painter = painterResource(DesignRes.drawable.btn_google_login),
            contentDescription = stringResource(DesignRes.string.google_sign_in_button_description),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp),
        )
    }
}

@Preview
@Composable
private fun GoogleSignInButtonPreview() {
    AppTheme {
        GoogleSignInButton(onClick = {})
    }
}
