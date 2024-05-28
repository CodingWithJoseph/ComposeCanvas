package com.josephhowerton.composecanvas.common.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.josephhowerton.composecanvas.common.theme.BreakfastColor
import com.josephhowerton.composecanvas.common.theme.DinnerColor
import com.josephhowerton.composecanvas.common.theme.FatColor
import com.josephhowerton.composecanvas.common.theme.FatColorTransparent
import com.josephhowerton.composecanvas.common.theme.Gray50A
import com.josephhowerton.composecanvas.common.theme.LunchColor
import com.josephhowerton.composecanvas.common.theme.SnackColor

@Composable
fun CircleProgressIndicator(
    progress: Double,
    strokeWidth: Float,
    size: Dp,
    progressColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.wrapContentSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(5.dp)
                .size(size)
        ) {
            CircularProgress(
                progress = progress,
                progressColor = progressColor,
                trackColor = trackColor,
                strokeWidth = strokeWidth,
                modifier = Modifier.size(size)
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = progressColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(150.dp)
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun CircularProgress(
    progress: Double,
    strokeWidth: Float,
    progressColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier,
) {

    val validProgress = if (progress.isNaN()) 0.0 else progress
    val sweepAngle = (360 * validProgress).toFloat()
    val animatedSweepAngle by remember { mutableStateOf(Animatable(sweepAngle)) }

    LaunchedEffect(animatedSweepAngle) {
        animatedSweepAngle.animateTo(
            targetValue = sweepAngle,
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
        )
    }

    LaunchedEffect(progress) {
        animatedSweepAngle.animateTo(
            targetValue = sweepAngle,
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
        )
    }

    Canvas(modifier = modifier) {

        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = animatedSweepAngle.value,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun DailyCalorieProgressIndicator(
    size: Int,
    breakfastProgress: Double,
    lunchProgress: Double,
    snackProgress: Double,
    dinnerProgress: Double,
    modifier: Modifier = Modifier,
) {

    val progress by remember { mutableDoubleStateOf(breakfastProgress+lunchProgress+snackProgress+dinnerProgress) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.wrapContentSize()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(size.dp)) {
            DailyCalorieProgress(
                breakfastProgress = breakfastProgress,
                lunchProgress = lunchProgress,
                snackProgress = snackProgress,
                dinnerProgress = dinnerProgress,
                size = size,
                modifier = Modifier.size(size.dp)
            )

            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 16.sp,
                fontWeight= FontWeight.SemiBold,
                color = BreakfastColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(150.dp)
                    .padding(5.dp)
            )
        }
    }
}


@Composable
private fun DailyCalorieProgress(
    modifier: Modifier = Modifier,
    breakfastProgress: Double,
    lunchProgress: Double,
    snackProgress: Double,
    dinnerProgress: Double,
    size: Int,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val breakfastEndAngle = 360 * breakfastProgress
        val snackEndAngle = breakfastEndAngle + 360 * snackProgress
        val lunchEndAngle = snackEndAngle + 360 * lunchProgress
        val dinnerEndAngle = lunchEndAngle + 360 * dinnerProgress

        val breakfastAnimate by animateFloatAsState(
            targetValue = breakfastEndAngle.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing), label = ""
        )
        val snackAnimate by animateFloatAsState(
            targetValue = snackEndAngle.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing), label = ""
        )
        val lunchAnimate by animateFloatAsState(
            targetValue = lunchEndAngle.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing), label = ""
        )
        val dinnerAnimate by animateFloatAsState(
            targetValue = dinnerEndAngle.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing), label = ""
        )

        Canvas(modifier = Modifier.size(size.dp)) {
            drawArc(
                color = Gray50A,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 20f)
            )

            drawArc(
                color = DinnerColor,
                startAngle = -90f + lunchEndAngle.toFloat(),
                sweepAngle = dinnerAnimate - lunchEndAngle.toFloat(),
                useCenter = false,
                style = Stroke(width = 20f)
            )

            drawArc(
                color = LunchColor,
                startAngle = -90f + snackEndAngle.toFloat(),
                sweepAngle = lunchAnimate - snackEndAngle.toFloat(),
                useCenter = false,
                style = Stroke(width = 20f)
            )

            drawArc(
                color = SnackColor,
                startAngle = -90f + breakfastEndAngle.toFloat(),
                sweepAngle = snackAnimate - breakfastEndAngle.toFloat(),
                useCenter = false,
                style = Stroke(width = 20f)
            )

            drawArc(
                color = BreakfastColor,
                startAngle = -90f,
                sweepAngle = breakfastAnimate,
                useCenter = false,
                style = Stroke(width = 20f)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CircleProgressIndicatorPreview() {
    CircleProgressIndicator(
        progress = 0.5,
        strokeWidth = 20f,
        size = 100.dp,
        progressColor = FatColor,
        trackColor = FatColorTransparent
    )
}

@Composable
@Preview(showBackground = true)
private fun DailyCalorieProgressIndicatorPreview(){
    DailyCalorieProgressIndicator(
        breakfastProgress = .25,
        lunchProgress = .25,
        snackProgress = .10,
        dinnerProgress = .13,
        size = 100,
    )
}
