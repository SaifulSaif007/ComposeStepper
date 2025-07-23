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
                    hasPreviousStep = index > 0,
                    state = when {
                        index == activeStep - 1 -> StepState.ACTIVE
                        index > activeStep - 1 -> StepState.UPCOMING
                        index < activeStep - 1 -> StepState.DONE
                        else -> StepState.ACTIVE
                    },
                    stepValue = index + 1,
                    rightSideContent = item.rightSideContent,
                    leftSideContent = item.leftSideContent,
                )
            }

        }
    }
}


@Composable
private fun StepperItem(
    modifier: Modifier,
    hasNextStep: Boolean,
    hasPreviousStep: Boolean,
    state: StepState,
    stepValue: Int,
    rightSideContent: @Composable () -> Unit,
    leftSideContent: @Composable () -> Unit,
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (prefixItem, circle, content, downLine, upLine) = createRefs()

        Column(
            modifier = Modifier.constrainAs(prefixItem) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            },
        ) {
            leftSideContent()
        }

        if (hasPreviousStep) VerticalDivider(
            modifier = Modifier
                .constrainAs(upLine) {
                    top.linkTo(parent.top)
                    bottom.linkTo(circle.top, margin = 4.dp)
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    height = Dimension.fillToConstraints
                },
            thickness = 4.dp,
            color = when (state) {
                StepState.ACTIVE -> Color.Blue
                StepState.DONE -> Color.Blue
                StepState.UPCOMING -> Color.Gray
            }
        )

        Box(
            modifier = Modifier
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
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


        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(circle.end, margin = 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(bottom = 8.dp),
        ) {
            rightSideContent()
        }


        if (hasNextStep) VerticalDivider(
            modifier = Modifier
                .constrainAs(downLine) {
                    top.linkTo(circle.bottom, margin = 4.dp)
                    bottom.linkTo(parent.bottom)
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
        hasPreviousStep = true,
        state = StepState.ACTIVE,
        stepValue = 1,
        rightSideContent = {
            Text("Watch me here")
        },
        leftSideContent = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun StepperPreview() {
    VerticalStepper(
        numberOfSteps = 4,
        activeStep = 3,
        stepItems = listOf(
            StepItem(
                rightSideContent = {
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Step 1")
                        Text("Step 1 has some subtitle")
                    }
                }
            ),
            StepItem(
                rightSideContent = {
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Step 2")
                        Text("Step 2 has some subtitle")
                    }
                }
            ),
            StepItem(
                rightSideContent = {
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Step 3")
                        Text("Step 3 has some subtitle")
                    }
                }
            ),
            StepItem(
                rightSideContent = {
                    Column(
                        modifier = Modifier.padding(2.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Step 4")
                        Text("Step 4 has some subtitle")
                    }
                }
            ),
        )
    )
}

data class StepItem(
    val rightSideContent: @Composable () -> Unit,
    val leftSideContent: @Composable () -> Unit = {
        Text("Prefix")
    },
)