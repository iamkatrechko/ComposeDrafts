package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round

class PointerInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                val position = remember { mutableStateOf(Offset(100f, 100f)) }
                Box(
                    Modifier
                        .fillMaxSize()
                        .pointerInput(position) {
                            forEachGesture {
                                awaitPointerEventScope {
                                    val down = awaitFirstDown()
                                    position.value = down.position
                                    drag(down.id) {
                                        position.value = it.position
                                    }
                                }
                            }
                        }
                ) {
                    val size = 40.dp
                    Box(
                        Modifier
                            .offset {
                                val halfPx = size.roundToPx() / 2
                                position.value.round() - IntOffset(halfPx, halfPx)
                            }
                            .size(size)
                            .background(Color.Green)
                    )
                }
            }
        }
    }
}
