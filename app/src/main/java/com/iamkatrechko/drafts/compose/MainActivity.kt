package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                Column {
                    Text(text = "Hello", color = MaterialTheme.colors.onSurface)

                    val colorToggleState = remember { mutableStateOf(true) }
                    val colorState = animateColorAsState(if (colorToggleState.value) MaterialTheme.colors.onSurface else Color.Green, animationSpec = tween(2000))
                    val secondTextState = remember { mutableStateOf("World") }
                    Text(text = secondTextState.value, color = colorState.value)
                    Button({
                        secondTextState.value = "User"
                        colorToggleState.value = !colorToggleState.value
                    }) {
                        Text("Click me")
                    }
                }
            }
        }
    }
}