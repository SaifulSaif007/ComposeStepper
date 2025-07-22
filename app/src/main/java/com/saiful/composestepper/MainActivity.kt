package com.saiful.composestepper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saiful.composestepper.ui.theme.ComposeStepperTheme
import com.saiful.stepper.HorizontalStepper
import com.saiful.stepper.StepItem
import com.saiful.stepper.VerticalStepper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeStepperTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var activeStepH = remember { mutableIntStateOf(0) }
                    var activeStepV = remember { mutableIntStateOf(0) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                            .padding(innerPadding),
                    )
                    {
                        Column(modifier = Modifier.padding(4.dp)) {
                            HorizontalStepper(
                                numberOfSteps = 4,
                                activeStep = activeStepH.intValue,
                                stepTitle = listOf(
                                    "step 1",
                                    "step 2",
                                    "step 3",
                                    "step 4"
                                )
                            )

                            Button(
                                onClick = {
                                    if (activeStepH.intValue < 4) {
                                        activeStepH.intValue += 1
                                    } else {
                                        activeStepH.intValue = 0
                                    }
                                },
                            ) {
                                Text("Next step")
                            }

                        }


                        Spacer(modifier = Modifier.size(10.dp))
                        
                        Column(modifier = Modifier.padding(4.dp)) {

                            VerticalStepper(
                                numberOfSteps = 4,
                                activeStep = activeStepV.intValue,
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

                            Button(
                                onClick = {
                                    if (activeStepV.intValue < 4) {
                                        activeStepV.intValue += 1
                                    } else {
                                        activeStepV.intValue = 0
                                    }
                                },
                            ) {
                                Text("Next step")
                            }

                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeStepperTheme {
        Greeting("Android")
    }
}