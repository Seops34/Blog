package com.seop.blog.wave

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@Composable
fun WavePane() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    // 피타고라스 정리를 이용하여 화면을 채울 수 있는 원의 반지를 계산한다.
    val radius = remember {
        val width = with(density) { configuration.screenWidthDp.dp.toPx() }
        val height = with(density) { configuration.screenHeightDp.dp.toPx() }
        sqrt(width * width + height * height) / 2
    }

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
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = radius * progress1.value,
                        alpha = (1f - progress1.value)
                    )
                }
        )
        Box(
            modifier = Modifier
                .drawBehind {
                    drawCircle(
                        color = Color.White,
                        radius = radius * progress2.value,
                        alpha = (1f - progress2.value)
                    )
                }
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