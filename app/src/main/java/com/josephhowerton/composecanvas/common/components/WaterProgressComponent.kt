package com.josephhowerton.composecanvas.common.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.josephhowerton.composecanvas.common.theme.BlueWaterPrimary
import com.josephhowerton.composecanvas.common.theme.BlueWaterSecondary

@Composable
fun WaterIndicator(progress: Double, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Water Intake",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BlueWaterPrimary,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            WaterProgressComponent(progress = progress, canvasSize = 300)
        }
    }
}

@Composable
private fun WaterProgressComponent(progress: Double, canvasSize: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(vertical = 15.dp)
            .width(canvasSize.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.width(canvasSize.dp)) {
            val wavePath = Path()
            val progressWavePath = Path()
            val waveAmplitude = size.width / 20
            val waveFrequency = size.width / 5

            wavePath.moveTo(0f, size.height / 2)
            progressWavePath.moveTo(0f, size.height / 2)

            var currentWidth = 0f
            var isWaveUp = true

            while (currentWidth < size.width) {
                val endX = currentWidth + waveFrequency / 2
                val controlX = currentWidth + waveFrequency / 4
                val controlY = if (isWaveUp) {
                    size.height / 2 + waveAmplitude
                } else {
                    size.height / 2 - waveAmplitude
                }

                wavePath.quadraticBezierTo(controlX, controlY, endX, size.height / 2)
                if (currentWidth <= size.width * progress) {
                    progressWavePath.quadraticBezierTo(controlX, controlY, endX, size.height / 2)
                }

                currentWidth = endX
                isWaveUp = !isWaveUp
            }

            drawPath(
                path = wavePath,
                color = BlueWaterSecondary,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )

            drawPath(
                path = progressWavePath,
                color = BlueWaterPrimary,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WaterProgressComponentPreview() = WaterProgressComponent(progress = 0.5, canvasSize = 300)
