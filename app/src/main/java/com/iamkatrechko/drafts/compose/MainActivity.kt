package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

                    var colorToggleState by remember { mutableStateOf(true) }
                    val colorState by animateColorAsState(if (colorToggleState) MaterialTheme.colors.onSurface else Color.Green, animationSpec = tween(2000))
                    var secondTextState by remember { mutableStateOf("World") }
                    Text(text = secondTextState, color = colorState)
                    Button({
                        secondTextState = "User"
                        colorToggleState = !colorToggleState
                    }) {
                        Text("Click me")
                    }
                    var textState by remember { mutableStateOf(2) }
                    TextButton(textState) { textState++ }
                }
            }
        }
    }
}

@Composable
fun TextButton(num: Int, onClick: () -> Unit) {
    Button(onClick) {
        Text("Count: $num")
    }
}