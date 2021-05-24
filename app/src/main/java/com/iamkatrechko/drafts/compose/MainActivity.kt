package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                Column {
                    Text(text = "Hello", color = MaterialTheme.colors.onSurface)

                    val secondTextState = remember { mutableStateOf("World") }
                    Text(text = secondTextState.value, color = MaterialTheme.colors.onSurface)
                    Button({
                        secondTextState.value = "User"
                    }) {
                        Text("Click me")
                    }
                }
            }
        }
    }
}