# Jetpack Compose Dashboard

This project demonstrates how to create a custom dashboard UI using Jetpack Compose. The dashboard includes various progress indicators for tracking health metrics such as calorie intake, macronutrient distribution (carbs, fiber, protein, fat), and water intake.

## Features

- **Circular Progress Indicators**: Visualize progress with custom circular progress components.
- **Water Progress Indicator**: Display water intake progress with a dynamic wave effect.
- **Reusable Components**: Modular and customizable components for easy integration and reuse across different projects.
- **Theming**: Consistent theming for a cohesive look and feel.

## Components

### CircularProgress

A basic circular progress component using the Canvas API.

```kotlin
@Composable
fun CircularProgress(
    progress: Double,
    strokeWidth: Float,
    progressColor: Color,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    val sweepAngle = (360 * progress).toFloat()

    Canvas(modifier = modifier.size(100.dp)) {
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
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}
