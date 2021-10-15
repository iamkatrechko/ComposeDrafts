package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

class ThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var theme by remember { mutableStateOf(true) }
            Column {
                Text(text = "Переключатель темы", color = Color.White)
                Checkbox(checked = theme, onCheckedChange = { theme = !theme })
                if (theme) {
                    MyTheme1 {
                        Content()
                    }
                } else {
                    MyTheme2 {
                        Content()
                    }
                }
            }
        }
    }

    @Composable
    private fun Content() {
        Column {
            Button(onClick = {}, colors = ButtonDefaults.buttonColors(backgroundColor = MyTheme.colors.successColor)) {
                Text("Текст кнопки")
            }
            Text(text = "Текст с ошибкой", color = MyTheme.colors.errorColor)
        }
    }
}