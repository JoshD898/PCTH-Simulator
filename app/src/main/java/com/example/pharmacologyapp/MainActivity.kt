package com.example.pharmacologyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pharmacologyapp.classes.Drug
import com.example.pharmacologyapp.classes.ExperimentData
import com.example.pharmacologyapp.classes.Receptor
import com.example.pharmacologyapp.ui.theme.PharmacologyAppTheme
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// Define the keys for storing data
private object PreferencesKeys {
    val DRUG_LIST = stringPreferencesKey("drug_list")
    val UNKNOWN_DRUG = stringPreferencesKey("unknown_drug")
    val UNKNOWN_DRUG_INDEX = stringPreferencesKey("unknown_drug_index")
    val RECEPTOR_LIST = stringPreferencesKey("receptor_list")
    val UNKNOWN_CONCENTRATION = floatPreferencesKey("unknown_concentration")
}

// Extension property to create DataStore
private val ComponentActivity.dataStore: DataStore<Preferences> by preferencesDataStore(name = "experiment_data")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Load persisted data
        val experimentData = runBlocking {
            loadExperimentData(dataStore)
        }

        setContent {
            PharmacologyAppTheme {
                MainApp(
                    initialExperimentData = experimentData,
                    onSaveExperimentData = { data: ExperimentData ->
                        runBlocking {
                            saveExperimentData(dataStore, data.drugList, data.unknownDrugPair, data.receptorList, data.unknownConcentration)
                        }
                    }
                )
            }
        }
    }

    // Function to load the experiment data from a dataStore
    private suspend fun loadExperimentData(dataStore: DataStore<Preferences>): ExperimentData {
        val preferences = dataStore.data.first()
        val drugListJson = preferences[PreferencesKeys.DRUG_LIST]
        val unknownDrugJson = preferences[PreferencesKeys.UNKNOWN_DRUG]
        val unknownDrugIndexJson = preferences[PreferencesKeys.UNKNOWN_DRUG_INDEX]
        val receptorListJson = preferences[PreferencesKeys.RECEPTOR_LIST]
        val unknownConcentration = preferences[PreferencesKeys.UNKNOWN_CONCENTRATION] ?: 0.0f

        // Deserialize JSON into objects
        val drugList = Gson().fromJson(drugListJson, Array<Drug>::class.java)?.toMutableList()
        val unknownDrug = Gson().fromJson(unknownDrugJson, Drug::class.java)
        val unknownDrugIndex = Gson().fromJson(unknownDrugIndexJson, Int::class.java)
        val receptorList = Gson().fromJson(receptorListJson, Array<Receptor>::class.java)?.toList()

        val unknownDrugPair = Pair(unknownDrug, unknownDrugIndex)

        return ExperimentData(drugList, unknownDrugPair, receptorList, unknownConcentration)
    }

    // Function to save the data to DataStore
    private suspend fun saveExperimentData(
        dataStore: DataStore<Preferences>,
        drugList: List<Drug>?,
        unknownDrugPair: Pair<Drug?, Int?>,
        receptorList: List<Receptor>?,
        unknownConcentration: Float
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DRUG_LIST] = Gson().toJson(drugList)
            preferences[PreferencesKeys.UNKNOWN_DRUG] = Gson().toJson(unknownDrugPair.first)
            preferences[PreferencesKeys.UNKNOWN_DRUG_INDEX] = Gson().toJson(unknownDrugPair.second)
            preferences[PreferencesKeys.RECEPTOR_LIST] = Gson().toJson(receptorList)
            preferences[PreferencesKeys.UNKNOWN_CONCENTRATION] = unknownConcentration
        }
    }
}



















