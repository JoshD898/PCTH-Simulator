package com.example.pharmacologyapp.classes

import java.io.Serializable

/**
 * A serializable class for storing the experiment data
 *
 * @param drugList The list of drugs for the experiment
 * @param unknownDrugPair A pair of the unknown drug and the list index of it's origin
 * @param receptorList The list of receptors for the experiment
 * @param unknownConcentration The concentration of the unknown drug
 */
data class ExperimentData(
    val drugList: MutableList<Drug>?,
    val unknownDrugPair: Pair<Drug?, Int?>,
    val receptorList: List<Receptor>?,
    val unknownConcentration: Float
) : Serializable