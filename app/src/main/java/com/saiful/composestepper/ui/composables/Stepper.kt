package com.saiful.composestepper.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Stepper() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {

        Row {
            repeat(times = 4) {
                StepperItem(
                    modifier = if (it != 3) Modifier.weight(1f) else Modifier,
                    hasNextStep = it != 3,
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White // Set the color of the icon
                        )

                    }
                )
            }
        }

    }

}


@Composable
private fun StepperItem(
    modifier: Modifier,
    hasNextStep: Boolean,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
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
            content()
        }

        if (hasNextStep) Box(
            modifier = Modifier
                .padding(4.dp)
                .height(4.dp)
                .weight(1f)
                .background(Color.Blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StepperPreview() {
    Stepper()
}