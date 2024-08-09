package com.example.pharmacologyapp.classes

import java.io.Serializable

/**
 * A serializable class for storing the experiment data
 *
 * @param unknownDrugPair A pair of the unknown drug and the list index of it's origin
 * @param unknownConcentration The concentration of the unknown drug
 */
data class ExperimentData(
    val unknownDrugPair: Pair<Drug?, Int?>,
    val unknownConcentration: Float
) : Serializable