package com.iamkatrechko.drafts.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.exponentialDecay
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlinx.coroutines.launch

class PointerInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
            ) {
                val position = remember { Animatable(Offset(100f, 100f), Offset.VectorConverter) }
                val scope = rememberCoroutineScope()
                val smallBoxSize = 40.dp

                Box(
                    Modifier
                        .fillMaxSize()
                        .pointerInput(position) {
                            val velocityTracker = VelocityTracker()
                            val halfSizePx = smallBoxSize.toPx() / 2
                            position.updateBounds(
                                Offset(halfSizePx, halfSizePx),
                                Offset(size.width - halfSizePx, size.height - halfSizePx)
                            )
                            forEachGesture {
                                velocityTracker.resetTracking()
                                awaitPointerEventScope {
                                    val down = awaitFirstDown()
                                    scope.launch {
                                        position.snapTo(down.position)
                                    }
                                    drag(down.id) {
                                        velocityTracker.addPosition(it.uptimeMillis, it.position)
                                        scope.launch {
                                            position.snapTo(it.position)
                                        }
                                    }
                                }
                                val velocity = velocityTracker.calculateVelocity()
                                scope.launch {
                                    var initialVelocity = Offset(velocity.x, velocity.y)
                                    do {
                                        val result = position.animateDecay(initialVelocity, exponentialDecay())
                                        initialVelocity = result.endState.velocity
                                        if (position.value.x == position.lowerBound?.x ||
                                            position.value.x == position.upperBound?.x) {
                                            initialVelocity = initialVelocity.copy(x = -initialVelocity.x)
                                        }
                                        if (position.value.y == position.lowerBound?.y ||
                                            position.value.y == position.upperBound?.y) {
                                            initialVelocity = initialVelocity.copy(y = -initialVelocity.y)
                                        }
                                    } while (result.endReason == AnimationEndReason.BoundReached)
                                }
                            }
                        }
                ) {
                    Box(
                        Modifier
                            .offset {
                                val halfPx = smallBoxSize.roundToPx() / 2
                                position.value.round() - IntOffset(halfPx, halfPx)
                            }
                            .size(smallBoxSize)
                            .background(Color.Green)
                    )
                }
            }
        }
    }
}
