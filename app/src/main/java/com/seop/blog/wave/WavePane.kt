package com.seop.blog.wave

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@Composable
fun WavePane() {
    val progress1 = remember { Animatable(0f) }
    val progress2 = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            val job1 = launch {
                progress1.snapTo(0f)
                progress1.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 4_000,
                        easing = LinearEasing
                    )
                )
            }
            val job2 = launch {
                progress2.snapTo(0f)
                progress2.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 4_000,
                        // delayMillis를 이용해, 각 애니메이션 노출 순서를 제어한다.
                        delayMillis = 1_000,
                        easing = LinearEasing
                    )
                )
            }
            // 모든 애니메이션이 완료될 때까지 대기한다.
            joinAll(job1, job2)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size = 300.dp * progress1.value)
                .background(
                    color = Color.White.copy(alpha = 1f - progress1.value),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(size = 300.dp * progress2.value)
                .background(
                    color = Color.White.copy(alpha = 1f - progress1.value),
                    shape = CircleShape
                )
        )
    }
}

@Preview
@Composable
private fun PreviewWavePane() {
    MaterialTheme {
        WavePane()
    }
}