package com.saiful.stepper

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun VerticalStepper(
    numberOfSteps: Int,
    activeStep: Int,
    stepTitle: List<String>
) {
    Row(
        modifier = Modifier
            .padding(12.dp),
    ) {

        Column {
            repeat(times = numberOfSteps) {
                StepperItem(
                    modifier = Modifier,
                    hasNextStep = it != numberOfSteps - 1,
                    hasPrevious = it != 0,
                    state = when {
                        it == activeStep - 1 -> StepState.ACTIVE
                        it > activeStep - 1 -> StepState.UPCOMING
                        it < activeStep - 1 -> StepState.DONE
                        else -> StepState.ACTIVE
                    },
                    stepTitle = stepTitle[it],
                    stepValue = it + 1
                )
            }
        }

    }
}


@Composable
private fun StepperItem(
    modifier: Modifier,
    hasNextStep: Boolean,
    hasPrevious: Boolean,
    state: StepState,
    stepTitle: String,
    stepValue: Int
) {

    ConstraintLayout(modifier = modifier) {
        val (circle, line2, text) = createRefs()


        Box(
            modifier = Modifier
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .size(32.dp)
                .drawBehind {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val circleRadius = (size.width / 2).toDp().toPx()
                    drawCircle(
                        color = when (state) {
                            StepState.ACTIVE -> Color.Blue
                            StepState.DONE -> Color.Blue
                            StepState.UPCOMING -> Color.Gray
                        },
                        center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                        radius = circleRadius
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (state == StepState.DONE) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White // Set the color of the icon
                )
            } else {
                Text(
                    modifier = Modifier,
                    text = stepValue.toString(),
                    style = TextStyle(color = Color.White)
                )
            }
        }

        Text(
            modifier = Modifier.constrainAs(text) {
                top.linkTo(circle.top)
                start.linkTo(circle.end, margin = 8.dp)
                bottom.linkTo(circle.bottom)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            },
            text = stepTitle,

            )

        if (hasNextStep) VerticalDivider(
            modifier = Modifier
                .constrainAs(line2) {
                    top.linkTo(circle.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 4.dp)
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    height = Dimension.value(20.dp)
                },
            thickness = 4.dp,
            color = when (state) {
                StepState.DONE -> Color.Blue
                StepState.ACTIVE -> Color.Gray
                StepState.UPCOMING -> Color.Gray
            }
        )
    }


}


@Preview(showBackground = true)
@Composable
private fun StepperItemPreview() {
    StepperItem(
        modifier = Modifier,
        hasNextStep = true,
        hasPrevious = false,
        state = StepState.ACTIVE,
        stepTitle = "step 1",
        stepValue = 1
    )
}

@Preview(showBackground = true)
@Composable
private fun StepperPreview() {
    VerticalStepper(
        numberOfSteps = 4,
        activeStep = 4,
        stepTitle = listOf(
            "step 1",
            "step 2",
            "step 3",
            "step 4"
        )
    )
}