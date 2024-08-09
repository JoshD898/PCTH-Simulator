package com.example.pharmacologyapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pharmacologyapp.classes.Drug

/**
 * A composable function the displays the menu for drug selection in a 3x2 button grid, with the unknown drug included as an option.
 *
 * @param isDrugMenuVisible A boolean that controls whether the menu is displayed
 * @param drugList A list of drugs to choose from. Must be length 6.
 * @param onDrugSelected The function that is called when a button is pressed
 * @param showUnknown Boolean that determines if the unknown drug is shown as an option
 * @param modifier Modifier to be applied
 */
@Composable
fun DrugMenu(
    isDrugMenuVisible: Boolean = false,
    drugList: List<Drug>,
    onDrugSelected: (Int) -> Unit,
    showUnknown: Boolean = false,
    modifier: Modifier = Modifier
) {
    if(isDrugMenuVisible) {
        Surface(modifier = modifier.fillMaxSize()) {
            Column {
                Row(modifier = modifier
                    .fillMaxSize()
                    .weight(2f)
                ) {
                    DrugMenuButton(index = 0, drug = drugList[0], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                    DrugMenuButton(index = 1, drug = drugList[1], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                    DrugMenuButton(index = 2, drug = drugList[2], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                }
                Row(modifier = modifier
                    .fillMaxSize()
                    .weight(2f)
                ) {
                    DrugMenuButton(index = 3, drug = drugList[3], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                    DrugMenuButton(index = 4, drug = drugList[4], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                    DrugMenuButton(index = 5, drug = drugList[5], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                }
                if (showUnknown) {
                    DrugMenuButton(index = 6, drug = drugList[6], onDrugSelected = onDrugSelected, modifier = modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun DrugMenuButton(
    index: Int,
    modifier: Modifier = Modifier,
    drug: Drug,
    onDrugSelected: (Int) -> Unit
) {
    Button(
        onClick = {onDrugSelected(index)},
        modifier = modifier
            .fillMaxSize(),
        colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray, // Ensure the background color is applied
            contentColor = Color.White // Set the text color
        ),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(0.dp, Color.LightGray)
    ){
        Text(text = drug.name)
    }
}



