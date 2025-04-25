package com.saiful.composestepper.ui.composables

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun Stepper() {
    Row(
        modifier = Modifier
            .padding(12.dp),
    ) {

        Row {
            repeat(times = 3) {
                StepperItem(
                    modifier = if (it != 2) Modifier.weight(1f) else Modifier,
                    hasNextStep = it != 2
                )
            }
        }

    }

}


@Composable
private fun StepperItem(
    modifier: Modifier,
    hasNextStep: Boolean,
) {

    ConstraintLayout(modifier = modifier.padding(vertical = 4.dp, horizontal = 2.dp)) {
        val (circle, line, text) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .wrapContentSize()
                .drawBehind {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val circleRadius = (size.width / 2).toDp().toPx()
                    drawCircle(
                        color = Color.Red,
                        center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                        radius = circleRadius
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "Add",
                modifier = Modifier.size(24.dp),
                tint = Color.White // Set the color of the icon
            )
        }

        Text(
            modifier = Modifier.constrainAs(text) {
                top.linkTo(circle.bottom, margin = 2.dp)
                start.linkTo(circle.start)
                bottom.linkTo(parent.bottom)
            },
            text = "item"
        )

        if (hasNextStep) HorizontalDivider(
            modifier = Modifier
                .constrainAs(line) {
                    top.linkTo(circle.top)
                    bottom.linkTo(circle.bottom)
                    start.linkTo(circle.end, margin = 4.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .background(Color.Blue)
        )
    }


}

@Preview(showBackground = true)
@Composable
private fun StepperItemPreview() {
    StepperItem(
        modifier = Modifier,
        hasNextStep = true
    )
}

@Preview(showBackground = true)
@Composable
private fun StepperPreview() {
    Stepper()
}