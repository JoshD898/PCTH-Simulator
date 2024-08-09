package com.example.pharmacologyapp

import com.example.pharmacologyapp.classes.Drug
import com.example.pharmacologyapp.classes.Receptor

/**
 * Initializes a list of six drugs for the experiment, corresponding to three receptors.
 *
 * The efficacy, halfMax and dissociationConstant values aren't based on any real drugs.
 *
 * The values are chosen within a range that shows nice effects when 1mL of 1uM drug is added to the bath.
 */
fun initializeExperimentDrugs(): MutableList<Drug> {
    return(mutableListOf<Drug>(
        Drug(name = "Agonist 1", efficacy = 0.78f, halfMax = 0.1f),
        Drug(name = "Agonist 2", efficacy = 0.64f, halfMax = 0.15f),
        Drug(name = "Agonist 3", efficacy = 0.97f, halfMax = 0.17f),
        Drug(name = "Antagonist 1", dissociationConstant = 0.12f),
        Drug(name = "Antagonist 2", dissociationConstant = 0.09f),
        Drug(name = "Antagonist 3", dissociationConstant = 0.17f)
    ))
}

/**
 * Chooses and duplicates a random drug from a list of Drugs, returns the a pair of the new Drug and the index of the drug is was duplicated from.
 *
 * @param drugList The list of drugs
 */
fun createUnknownDrug(drugList: List<Drug>): Pair<Drug, Int> {
    val randomIndex = (drugList.indices).random()
    val selectedDrug = drugList[randomIndex]
    val unknownDrug = Drug(
        name = "Unknown",
        concentration = selectedDrug.concentration,
        efficacy = selectedDrug.efficacy,
        halfMax = selectedDrug.halfMax,
        dissociationConstant = selectedDrug.dissociationConstant
    )
    return Pair(unknownDrug, randomIndex)
}

/**
 * Initializes a list of receptors for the experiment
 *
 * @param drugList The list of stock drugs
 * @param unknownDrugPair A pair of the unknown drug and the index of the matching drug from drugList
 */
fun initializeExperimentReceptors(drugList: List<Drug>, unknownDrugPair: Pair<Drug, Int>): List<Receptor> {
    val (unknownDrug, unknownIndex) = unknownDrugPair

    return mutableListOf(
        Receptor(
            name = "Receptor 1",
            agonists = if (unknownIndex == 0) listOf(drugList[0], unknownDrug) else listOf(drugList[0]),
            antagonists = if (unknownIndex == 3) listOf(drugList[3], unknownDrug) else listOf(drugList[3])
        ),
        Receptor(
            name = "Receptor 2",
            agonists = if (unknownIndex == 1) listOf(drugList[1], unknownDrug) else listOf(drugList[1]),
            antagonists = if (unknownIndex == 4) listOf(drugList[4], unknownDrug) else listOf(drugList[4])
        ),
        Receptor(
            name = "Receptor 3",
            agonists = if (unknownIndex == 2) listOf(drugList[2], unknownDrug) else listOf(drugList[2]),
            antagonists = if (unknownIndex == 5) listOf(drugList[5], unknownDrug) else listOf(drugList[5])
        )
    )
}

/**
 * Updates the tension fractions of all receptors in the list
 *
 * @param receptors The list of receptors for the experiment
 */
fun updateTensionFractions(receptors: List<Receptor>) {
    for (receptor in receptors) {
        receptor.updateTension()
    }
}

/**
 * Returns the largest tension fraction of all the receptors in the list.
 *
 * @param receptors The list of receptors for the experiment
 */
fun largestTensionFraction(receptors: List<Receptor>):Float {
    var maxTensionFraction = 0f

    for (receptor in receptors) {
        if (receptor.tensionFraction > maxTensionFraction) {
            maxTensionFraction = receptor.tensionFraction
        }
    }
    return maxTensionFraction
}

/**
 * Resets the concentration of all drugs in the bath
 *
 * @param receptors The list of receptors for the experiment
 */
fun resetConcentrations(receptors: List<Receptor>) {
    for (receptor in receptors) {
        for (drug in receptor.agonists) {
                drug.concentration = 0f
        }
        for (drug in receptor.antagonists) {
                drug.concentration = 0f
        }
    }
}

/**
 * Checks if the user's provided answers for the unknown identity and concentration are correct.
 * The concentration guess is deemed correct if it is within 2% of the true value.
 *
 * @param drugGuessIndex The list index of the user's answer for the drug identity
 * @param concentrationGuess The user's answer for the unknown concentration
 * @param trueDrugIndex The list index of the unknown drug identity
 * @param trueConcentration The unknown drug concentration
 * @param forgiveness Acceptable percent error in the concentrationGuess
 */
fun isCorrectAnswer(
    drugGuessIndex: Int,
    concentrationGuess: Float,
    trueDrugIndex: Int,
    trueConcentration: Float,
    forgiveness: Float = 0.02f ) : Boolean {

    return (concentrationGuess >= trueConcentration * (1 - forgiveness) &&
            concentrationGuess <= trueConcentration * (1 + forgiveness) &&
            drugGuessIndex == trueDrugIndex)
    }