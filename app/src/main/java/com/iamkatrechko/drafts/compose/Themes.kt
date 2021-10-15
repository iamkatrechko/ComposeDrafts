package com.iamkatrechko.drafts.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class MyThemeColors(
    val successColor: Color,
    val errorColor: Color
)

object MyTheme {

    val colors: MyThemeColors
        @Composable
        get() = LocalMyThemeColors.current
}

val LocalMyThemeColors = staticCompositionLocalOf<MyThemeColors> { error("No provided") }

@Composable
fun MyTheme1(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalMyThemeColors provides MyThemeColors(Color.Blue, Color.Red),
        content = content
    )
}

@Composable
fun MyTheme2(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalMyThemeColors provides MyThemeColors(Color.Green, Color.Magenta),
        content = content
    )
}
