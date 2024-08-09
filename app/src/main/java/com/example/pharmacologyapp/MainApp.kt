package com.example.pharmacologyapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.yml.charts.common.model.Point
import com.example.pharmacologyapp.classes.ExperimentData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    initialExperimentData: ExperimentData,
    onSaveExperimentData: (ExperimentData) -> Unit
) {

    // Start by initializing the variables
    var drugList = remember { initializeExperimentDrugs() }
    var unknownDrugPair = remember { createUnknownDrug(drugList = drugList) }
    drugList.add(unknownDrugPair.first) // Add the unknown drug to the drug list
    var receptorList = remember { initializeExperimentReceptors(drugList = drugList, unknownDrugPair = unknownDrugPair) }
    var unknownConcentration = remember { Random.nextFloat() + 0.5f }

    // If data has been saved, overwrite the initialized variables
    if (initialExperimentData.drugList != null) {
        drugList = initialExperimentData.drugList
        unknownDrugPair = Pair(initialExperimentData.unknownDrugPair.first!!, initialExperimentData.unknownDrugPair.second!!)
        receptorList = initialExperimentData.receptorList!!
        unknownConcentration = initialExperimentData.unknownConcentration
    }



    // Menu display booleans
    var isSideDrugMenuVisible by remember { mutableStateOf(false) }
    var isConcentrationInputVisible by remember { mutableStateOf(false) }
    var isVolumeInputVisible by remember { mutableStateOf(false) }
    var isAnswerMessageVisible by remember { mutableStateOf(false) }
    var isAnswerDrugMenuVisible by remember { mutableStateOf(false) }
    var isAnswerConcentrationInputVisible by remember { mutableStateOf(false) }



    // Initialize variables and functions for the experiment
    var selectedDrugIndex by remember { mutableIntStateOf(0) }
    var currentConcentration by remember { mutableFloatStateOf(1.0f) }
    var currentVolume by remember { mutableFloatStateOf(1.0f) }
    var currentTension by remember { mutableFloatStateOf(0.0f) }
    var answerDrugIndex by remember { mutableIntStateOf(0) }
    var answerConcentration by remember { mutableFloatStateOf(0f) }
    val maxTension = 5f
    val ySteps = 5
    val graphData = remember { mutableStateListOf(Point(0f, maxTension), Point(0f, 0f)) }
    val scope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    fun launchJob() {
        job = scope.launch {
            while (true) {
                graphData.add(Point(graphData.size.toFloat() - 1, currentTension))
                delay(500)
            }
        }
    }

    fun randomizeUnknown() {
        unknownConcentration = Random.nextFloat() + 0.5f
        drugList.remove(unknownDrugPair.first) // Remove the unknown from drugList
        unknownDrugPair = createUnknownDrug(drugList = drugList)
        drugList.add(unknownDrugPair.first) // Add the new unknown to drugList
        receptorList = initializeExperimentReceptors(drugList = drugList, unknownDrugPair = unknownDrugPair) // Re-initialize the receptors
        onSaveExperimentData(ExperimentData(drugList, unknownDrugPair, receptorList, unknownConcentration)) // Save the update
    }

    LaunchedEffect(unknownDrugPair, drugList, receptorList, unknownConcentration) {
        onSaveExperimentData(ExperimentData(drugList, unknownDrugPair, receptorList, unknownConcentration))
    }


    Row(modifier = modifier.fillMaxSize()){

        SideMenu(
            modifier = modifier.weight(1f),
            drug = drugList[selectedDrugIndex],
            concentration = currentConcentration,
            volume = currentVolume,
            onChangeDrugClicked = {isSideDrugMenuVisible = true},
            onChangeConcentrationClicked = {isConcentrationInputVisible = true},
            onChangeVolumeClicked = {isVolumeInputVisible = true},
            onAddToBathClicked = {
                drugList[selectedDrugIndex].changeConcentration(stockConcentration = currentConcentration, stockVolume = currentVolume, bathVolume = 25f, unknownConcentration = unknownConcentration) // Update the concentration of the chosen drug
                updateTensionFractions(receptors = receptorList) // Update the tension fraction of all receptors
                currentTension = maxTension * largestTensionFraction(receptorList) // Update currentTension
            },
            onDrainBathClicked = { // Reset all concentrations when this button is clicked
                resetConcentrations(receptorList)
                updateTensionFractions(receptors = receptorList)
                currentTension = maxTension * largestTensionFraction(receptorList)
             },
            onStartRecordingClicked = { if (job == null || !job!!.isActive) { launchJob() } },
            onStopRecordingClicked = { if (job!!.isActive) { job!!.cancel() } },
            onResetGraphClicked = { resetPointData(graphData) },
            onRandomizeUnknownClicked = { randomizeUnknown() }
        )

        Column(modifier = modifier
            .weight(1f)
            .fillMaxSize()
        ){
            CheckAnswerInterface(
                modifier = modifier.weight(3f),
                chosenDrug = drugList[answerDrugIndex],
                chosenConcentration = answerConcentration,
                onChooseDrugClicked = { isAnswerDrugMenuVisible = true },
                onChooseConcentrationClicked = { isAnswerConcentrationInputVisible = true},
                onCheckAnswerClicked = { isAnswerMessageVisible = true }
                )
            LineGraph(modifier.weight(6f), pointsData = graphData, yMax = maxTension, ySteps = ySteps) //
        }
    }

    DrugMenu( // For the side menu
        drugList = drugList,
        onDrugSelected = { index:Int ->
            selectedDrugIndex = index
            isSideDrugMenuVisible = false },
        showUnknown = true,
        isDrugMenuVisible = isSideDrugMenuVisible
    )

    DrugMenu( // For the answer menu
        drugList = drugList,
        onDrugSelected = { index:Int ->
            answerDrugIndex = index
            isAnswerDrugMenuVisible = false },
        isDrugMenuVisible = isAnswerDrugMenuVisible
    )

    NumberInput( // Side menu concentration input
        displayNumberInput = isConcentrationInputVisible,
        initialValue = currentConcentration,
        onValueChanged = { newConcentration -> currentConcentration = newConcentration },
        onDismiss = { isConcentrationInputVisible = false}
    )

    NumberInput( // Side menu volume input
        displayNumberInput = isVolumeInputVisible,
        initialValue = currentVolume,
        onValueChanged = { newVolume -> currentVolume = newVolume },
        onDismiss = { isVolumeInputVisible = false}
    )

    NumberInput( // Answer menu concentration input
        displayNumberInput = isAnswerConcentrationInputVisible,
        initialValue = answerConcentration,
        onValueChanged = { newConcentration -> answerConcentration = newConcentration },
        onDismiss = { isAnswerConcentrationInputVisible = false}
    )

    AnswerMessage(
        isAnswerCorrect = isCorrectAnswer(
            drugGuessIndex = answerDrugIndex,
            concentrationGuess = answerConcentration,
            trueDrugIndex = unknownDrugPair.second,
            trueConcentration = unknownConcentration
        ),
        showAlert = isAnswerMessageVisible,
        onDismiss = { isAnswerMessageVisible = false },
        )
}



@Preview(showBackground = true, widthDp = 620, heightDp = 320)
@Composable
fun InterfacePreview() {

}



















