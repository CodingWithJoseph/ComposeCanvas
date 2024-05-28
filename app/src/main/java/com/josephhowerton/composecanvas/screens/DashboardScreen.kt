package com.josephhowerton.composecanvas.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.josephhowerton.composecanvas.R
import com.josephhowerton.composecanvas.common.components.CircleProgressIndicator
import com.josephhowerton.composecanvas.common.components.DailyCalorieProgressIndicator
import com.josephhowerton.composecanvas.common.components.WaterIndicator
import com.josephhowerton.composecanvas.common.theme.BreakfastColor
import com.josephhowerton.composecanvas.common.theme.CarbColor
import com.josephhowerton.composecanvas.common.theme.CarbColorTransparent
import com.josephhowerton.composecanvas.common.theme.ComposeCanvasTheme
import com.josephhowerton.composecanvas.common.theme.DeepOrange
import com.josephhowerton.composecanvas.common.theme.DinnerColor
import com.josephhowerton.composecanvas.common.theme.FatColor
import com.josephhowerton.composecanvas.common.theme.FatColorTransparent
import com.josephhowerton.composecanvas.common.theme.FiberColor
import com.josephhowerton.composecanvas.common.theme.FiberColorTransparent
import com.josephhowerton.composecanvas.common.theme.LunchColor
import com.josephhowerton.composecanvas.common.theme.ProteinColor
import com.josephhowerton.composecanvas.common.theme.ProteinColorTransparent
import com.josephhowerton.composecanvas.common.theme.SnackColor

@Composable
fun DashboardScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                today, caloriesProgress, proteinProgress, fatProgress, carbProgress, fiberProgress, waterProgress
            ) = createRefs()

            val startGuide = createGuidelineFromStart(0.05f)
            val midGuide = createGuidelineFromStart(0.5f)
            val endGuide = createGuidelineFromEnd(0.05f)

            Text(
                text = "Today",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.constrainAs(today) {
                    top.linkTo(parent.top, 20.dp)
                    start.linkTo(startGuide)
                }
            )

            CalorieCounter(
                modifier = Modifier.constrainAs(caloriesProgress) {
                    end.linkTo(endGuide)
                    start.linkTo(startGuide)
                    top.linkTo(today.bottom, 10.dp)
                    width = Dimension.fillToConstraints
                }
            )

            MacroShort(
                title = "Carbs",
                icon = R.drawable.ic_bread,
                progress = 0.65,
                current = (.65*.45*1800),
                outerColor = CarbColor,
                innerColor = CarbColorTransparent,
                modifier = Modifier.constrainAs(carbProgress) {
                    end.linkTo(midGuide, 10.dp)
                    start.linkTo(startGuide)
                    top.linkTo(caloriesProgress.bottom, 10.dp)
                    width = Dimension.fillToConstraints
                }
            )

            MacroShort(
                title = "Fiber",
                icon = R.drawable.ic_fiber,
                progress = 0.43,
                current = (.43*30),
                outerColor = FiberColor,
                innerColor = FiberColorTransparent,
                modifier = Modifier.constrainAs(fiberProgress) {
                    end.linkTo(endGuide)
                    start.linkTo(midGuide, 10.dp)
                    top.linkTo(carbProgress.top)
                    width = Dimension.fillToConstraints
                }
            )

            MacroShort(
                title = "Protein",
                icon = R.drawable.ic_chicken,
                progress = 0.35,
                current = (.35*.35*1800),
                outerColor = ProteinColor,
                innerColor = ProteinColorTransparent,
                modifier = Modifier.constrainAs(proteinProgress) {
                    end.linkTo(midGuide, 10.dp)
                    start.linkTo(startGuide)
                    top.linkTo(carbProgress.bottom, 20.dp)
                    width = Dimension.fillToConstraints
                }
            )

            MacroShort(
                title = "Fat",
                icon = R.drawable.ic_butter,
                progress = 0.25,
                current = (.25*.35*1800),
                outerColor = FatColor,
                innerColor = FatColorTransparent,
                modifier = Modifier.constrainAs(fatProgress) {
                    end.linkTo(endGuide)
                    start.linkTo(midGuide, 10.dp)
                    top.linkTo(proteinProgress.top)
                    width = Dimension.fillToConstraints
                }
            )

            WaterIndicator(
                progress = 0.25,
                modifier = Modifier.constrainAs(waterProgress) {
                    start.linkTo(proteinProgress.start)
                    end.linkTo(fatProgress.end)
                    top.linkTo(fatProgress.bottom, 30.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun CalorieCounter(modifier: Modifier = Modifier) {
    Card(elevation = CardDefaults.cardElevation(2.dp), modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (titleId, iconId, currentValueId, targetValueId, progressId) = createRefs()
            val (breakfastId, snackId, lunchId, dinnerId) = createRefs()

            val midGuide = createGuidelineFromStart(0.45f)
            val endGuide = createGuidelineFromStart(0.80f)

            Text(
                text = "Calories",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.constrainAs(titleId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_fire),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .constrainAs(iconId) {
                        top.linkTo(parent.top)
                        start.linkTo(titleId.end, 5.dp)
                        bottom.linkTo(titleId.bottom)
                    }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.constrainAs(currentValueId) {
                    start.linkTo(midGuide)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_fork),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(FatColor),
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = "${(0.73 * 1800).toInt()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.constrainAs(targetValueId) {
                    top.linkTo(parent.top)
                    start.linkTo(endGuide)
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_flag),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(DeepOrange),
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = "1800g",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            DailyCalorieProgressIndicator(
                size = 125,
                breakfastProgress = 0.25,
                lunchProgress = 0.20,
                snackProgress = 0.08,
                dinnerProgress = 0.20,
                modifier = Modifier.constrainAs(progressId) {
                    top.linkTo(titleId.bottom, 25.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )

            MealTime(
                title = "Breakfast",
                value = (0.25 * 1800).toInt(),
                icon = R.drawable.ic_breakfast,
                color = BreakfastColor,
                modifier = Modifier.constrainAs(breakfastId) {
                    top.linkTo(progressId.top)
                    bottom.linkTo(snackId.top)
                    start.linkTo(midGuide)
                }
            )

            MealTime(
                title = "Lunch",
                value = (0.20 * 1800).toInt(),
                icon = R.drawable.ic_lunch,
                color = LunchColor,
                modifier = Modifier.constrainAs(snackId) {
                    top.linkTo(breakfastId.bottom)
                    bottom.linkTo(progressId.bottom)
                    start.linkTo(midGuide)
                }
            )

            MealTime(
                title = "Snack",
                value = (0.08 * 1800).toInt(),
                icon = R.drawable.ic_snack,
                color = SnackColor,
                modifier = Modifier.constrainAs(lunchId) {
                    top.linkTo(progressId.top)
                    bottom.linkTo(snackId.top)
                    start.linkTo(endGuide, 5.dp)
                }
            )

            MealTime(
                title = "Dinner",
                value = (0.20 * 1800).toInt(),
                icon = R.drawable.ic_dinner,
                color = DinnerColor,
                modifier = Modifier.constrainAs(dinnerId) {
                    top.linkTo(lunchId.bottom)
                    bottom.linkTo(progressId.bottom)
                    start.linkTo(endGuide, 5.dp)
                }
            )
        }
    }
}

@Composable
fun MacroShort(
    icon: Int,
    title: String,
    progress: Double,
    current: Double,
    innerColor: Color,
    outerColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(elevation = CardDefaults.cardElevation(2.dp), modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (titleId, iconId, currentValueId, progressId) = createRefs()

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.constrainAs(titleId) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .constrainAs(iconId) {
                        start.linkTo(titleId.end, 5.dp)
                        top.linkTo(currentValueId.top)
                        bottom.linkTo(currentValueId.bottom)
                    }
            )

            Text(
                text = "${current.toInt()}g",
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(currentValueId) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
            )

            CircleProgressIndicator(
                size = 115.dp,
                strokeWidth = 20f,
                progress = progress,
                trackColor = innerColor,
                progressColor = outerColor,
                modifier = Modifier.constrainAs(progressId) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(titleId.bottom, 10.dp)
                }
            )
        }
    }
}

@Composable
fun MealTime(
    title: String,
    value: Int,
    icon: Int,
    color: Color,
    modifier: Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(13.dp)
            )

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
            )
        }

        Text(
            text = "${value}g",
            fontSize = 13.sp,
            color = color,
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardPreview() = ComposeCanvasTheme { Surface { DashboardScreen() } }