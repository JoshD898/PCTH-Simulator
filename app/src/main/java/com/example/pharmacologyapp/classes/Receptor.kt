package com.example.pharmacologyapp.classes

import java.io.Serializable

/**
 * Represents a receptor with a method to update its tension fraction.
 *
 * Tension fraction * maximal tension = tension provided from receptor
 *
 * @param name The name of the receptor.
 * @param agonists The list of agonists affecting the receptor.
 * @param antagonists The list of antagonists affecting the receptor.
 * @param tensionFraction The current fraction of maximal tension provided by the receptor. Default is 0.0f.
 */
data class Receptor(
    val name: String,
    val agonists: List<Drug>,
    val antagonists: List<Drug>,
    var tensionFraction: Float = 0.0f
): Serializable {
    fun updateTension() {

        for (agonist in agonists) {

        }
        tensionFraction = ( agonists[0].efficacy * totalConcentration(agonists) /
                            (totalConcentration(agonists) + agonists[0].halfMax *
                            (1 + totalConcentration(antagonists) / antagonists[0].dissociationConstant)) )
    }

    private fun totalConcentration(drugs: List<Drug>): Float {
        var totalConcentration = 0.0f
        for (drug in drugs) {
            totalConcentration += drug.concentration
        }
        return totalConcentration
    }

}
