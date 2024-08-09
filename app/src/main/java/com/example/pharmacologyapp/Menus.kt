package com.example.pharmacologyapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pharmacologyapp.classes.Drug


/**
 * A composable function that displays the side menu of the pharmacology drug app
 *
 * @param modifier Modifier to be applied
 * @param drug The currently selected drug
 * @param concentration The currently selected concentration
 * @param volume The currently chosen volume
 * @param onChangeDrugClicked Function that allows user to select a Drug class
 * @param onChangeConcentrationClicked Function that allows user to change the concentration of the stock drug
 * @param onChangeVolumeClicked Function that allows user to change the volume of the stock drug
 * @param onAddToBathClicked Function called when drug is added to bath
 * @param onDrainBathClicked Function that is called when the bath is drained
 * @param onStartRecordingClicked Function that begin recording measurements
 * @param onStopRecordingClicked Function that pauses measurements
 * @param onResetGraphClicked Function that resets the graph
 * @param onRandomizeUnknownClicked Function that randomizes the unknown
 */
@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    drug: Drug,
    concentration: Float = 0.0f,
    volume: Float = 0.0f,
    onChangeDrugClicked: () -> Unit = {},
    onChangeConcentrationClicked: () -> Unit = {},
    onChangeVolumeClicked: () -> Unit = {},
    onAddToBathClicked: () -> Unit = {},
    onDrainBathClicked: () -> Unit = {},
    onStartRecordingClicked: () -> Unit = {},
    onStopRecordingClicked: () -> Unit = {},
    onResetGraphClicked: () -> Unit = {},
    onRandomizeUnknownClicked: () -> Unit = {}
    ) {
        Column(modifier = modifier.fillMaxSize()){
            MenuButton(text = "Drug: ${drug.name}", onClick = { onChangeDrugClicked() }, modifier = modifier.weight(1f))
            if (drug.name == "Unknown") {  // The unknown drug concentration is not shown and not editable
                MenuButton(text = "Concentration: ???", onClick = {}, modifier = modifier.weight(1f))
            }
            else {
                MenuButton(text = "Concentration: $concentration uM", onClick = { onChangeConcentrationClicked() }, modifier = modifier.weight(1f))
            }
            MenuButton(text = "Volume: $volume mL", onClick = { onChangeVolumeClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Add to bath", onClick = { onAddToBathClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Drain bath", onClick = { onDrainBathClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Start recording", onClick = { onStartRecordingClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Stop recording", onClick = { onStopRecordingClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Reset graph", onClick = { onResetGraphClicked() }, modifier = modifier.weight(1f))
            MenuButton(text = "Randomize unknown", onClick = { onRandomizeUnknownClicked() }, modifier = modifier.weight(1f))
        }
    }

/**
 * A composable function that displays the check answer menu of the pharmacology drug app
 *
 * @param modifier Modifier to be applied
 * @param onChooseDrugClicked Function that allows user to select a drug
 * @param onChooseConcentrationClicked Function that allows user to choose a concentration
 * @param onCheckAnswerClicked Function that checks if the user's answer is correct
 */
@Composable
fun CheckAnswerInterface(
    modifier:Modifier = Modifier,
    chosenDrug: Drug,
    chosenConcentration: Float,
    onChooseDrugClicked: () -> Unit = {},
    onChooseConcentrationClicked: () -> Unit = {},
    onCheckAnswerClicked: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        MenuButton(text = "Unknown identity: ${chosenDrug.name}", onClick = { onChooseDrugClicked() }, modifier = modifier.weight(1f))
        MenuButton(text = "Unknown concentration: $chosenConcentration uM", onClick = { onChooseConcentrationClicked() }, modifier = modifier.weight(1f))
        MenuButton(text = "Check answer", onClick = { onCheckAnswerClicked() }, modifier = modifier.weight(1f))
    }
}




@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
    ) {
        Button(
            onClick = { onClick() },
            modifier = modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray, // Ensure the background color is applied
                contentColor = Color.White // Set the text color
            ),
            shape = RoundedCornerShape(0.dp),
            border = BorderStroke(0.dp, Color.LightGray)
        ){
            Text(text = text)
        }
    }

@Preview(showBackground = true, widthDp = 620, heightDp = 320)
@Composable
fun SideMenuPreview(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()){
        SideMenu(modifier = modifier.weight(1f), drug = Drug(name = "Unknown"))
        Spacer(modifier = modifier.weight(1f))
    }
}