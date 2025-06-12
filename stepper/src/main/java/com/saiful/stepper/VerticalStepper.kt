package com.saiful.stepper

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
    stepItems: List<StepItem>
) {
    Row {
        LazyColumn {
            itemsIndexed(stepItems) { index, item ->
                StepperItem(
                    modifier = Modifier,
                    hasNextStep = index != numberOfSteps - 1,
                    state = when {
                        index == activeStep - 1 -> StepState.ACTIVE
                        index > activeStep - 1 -> StepState.UPCOMING
                        index < activeStep - 1 -> StepState.DONE
                        else -> StepState.ACTIVE
                    },
                    stepValue = index + 1,
                    stepTitle = item.stepTitle,
                    stepContent = item.stepContent,
                )
            }

        }
    }
}


@Composable
private fun StepperItem(
    modifier: Modifier,
    hasNextStep: Boolean,
    state: StepState,
    stepValue: Int,
    stepTitle: String,
    stepContent: @Composable () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        val (prefixItem, circle, title, content, line) = createRefs()

        Text(
            modifier = Modifier.constrainAs(prefixItem) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
            text = "Prefix"
        )

        Box(
            modifier = Modifier
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(prefixItem.end, margin = 4.dp)
                }
                .size(28.dp)
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
            modifier = Modifier.constrainAs(title) {
                top.linkTo(circle.top)
                start.linkTo(circle.end, margin = 8.dp)
                bottom.linkTo(circle.bottom)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            text = stepTitle,
        )

        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(circle.bottom)
                    start.linkTo(title.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 8.dp),
        ) {
            stepContent()
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
        }


        if (hasNextStep) VerticalDivider(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(content.top, margin = 4.dp)
                    bottom.linkTo(content.bottom)
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    height = Dimension.fillToConstraints
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
        state = StepState.ACTIVE,
        stepTitle = "step 1",
        stepValue = 1,
        stepContent = {
            Text("Watch me here")
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun StepperPreview() {
    VerticalStepper(
        numberOfSteps = 4,
        activeStep = 1,
        stepItems = listOf(
            StepItem("Step 1") {
                Text("Watch me here")
            },
            StepItem("Step 2") {
                Text("Watch me here")
            },
            StepItem("Step 3") {
                Text("Watch me here")
            },
            StepItem("Step 4") {
                Text("Watch me here")
            }
        )
    )
}

data class StepItem(
    val stepTitle: String,
    val stepContent: @Composable () -> Unit
)