package com.example.a176lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a176lablearnandroid.ui.theme._176LabLearnAndroidTheme

class Part3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _176LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutChart(
                            proportions = listOf(30f, 40f, 30f),
                            colors = listOf(Color.Red, Color.Green, Color.Blue),
                            modifier = Modifier.size(250.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 60f
) {
    var animationPlayed by remember { mutableStateOf(false) }

    val totalSweepAngle by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "sweep_animation"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    val totalProportion = proportions.sum()
    if (totalProportion == 0f) return

    Canvas(modifier = modifier) {
        var currentStartAngle = -90f
        var remainingSweep = totalSweepAngle

        for (i in proportions.indices) {
            val proportion = proportions[i]
            val color = colors[i]

            val sliceAngle = (proportion / totalProportion) * 360f

            if (remainingSweep <= 0f) break

            val actualSweep = minOf(sliceAngle, remainingSweep)

            drawArc(
                color = color,
                startAngle = currentStartAngle,
                sweepAngle = actualSweep,
                useCenter = false,
                style = Stroke(width = strokeWidth),
                size = Size(size.width, size.height)
            )

            currentStartAngle += sliceAngle
            remainingSweep -= sliceAngle
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonutChartPreview() {
    _176LabLearnAndroidTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                proportions = listOf(30f, 40f, 30f),
                colors = listOf(Color.Red, Color.Green, Color.Blue),
                modifier = Modifier.size(250.dp)
            )
        }
    }
}