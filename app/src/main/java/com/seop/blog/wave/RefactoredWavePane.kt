package com.seop.blog.wave

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RefactoredWavePane() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition_wave")
    val progress1 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1_000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "infinite_transition_wave_progress1"
    )
    val progress2 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1_000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(500)
        ),
        label = "infinite_transition_wave_progress2"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = 300.dp.toPx() * progress1.value,
                        alpha = 1f - progress1.value
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 300.dp.toPx() * progress2.value,
                        alpha = 1f - progress2.value
                    )
                }
        )
    }
}

@Preview
@Composable
private fun PreviewRefactoredWavePane() {
    MaterialTheme {
        RefactoredWavePane()
    }
}