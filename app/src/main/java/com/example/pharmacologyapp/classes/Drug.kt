package com.example.pharmacologyapp.classes

/**
 * Represents a drug with properties and methods to manage its concentration.
 *
 * @param name The name of the drug.
 * @param efficacy The efficacy of the drug (relevant for agonists only). Default is 0.0f.
 * @param halfMax The EC50 of the drug (relevant for agonists only). Default is 0.0f.
 * @param dissociationConstant The dissociation constant of the drug (relevant for antagonists only). Default is 0.0f.
 * @param concentration The current concentration of the drug. Default is 0.0f.
 */
data class Drug(
    val name: String,
    val efficacy: Float = 0.0f,
    val halfMax: Float = 0.0f,
    val dissociationConstant: Float = 0.0f,
    var concentration: Float = 0.0f
) {
    fun changeConcentration(stockVolume: Float, stockConcentration: Float, bathVolume: Float, unknownConcentration: Float) {
        concentration = if (name == "Unknown") {
            (concentration * bathVolume + stockVolume * unknownConcentration) / (stockVolume + bathVolume)
        } else {
            (concentration * bathVolume + stockVolume * stockConcentration) / (stockVolume + bathVolume)
        }
    }
    fun resetConcentration() {
        concentration = 0.0f
    }
}
