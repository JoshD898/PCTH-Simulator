package com.example.pharmacologyapp

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

/**
 * A composable function that uses AlertDialogue and TextField to receive input from the user
 *
 * @param displayNumberInput A boolean that controls whether the composable is visible
 * @param initialValue The initial value of what the input will be replacing
 * @param onValueChanged The function that replaces the initial value with the new value
 * @param onDismiss The function that changes the display boolean to hide the composable
 */
@Composable
fun NumberInput(
    displayNumberInput: Boolean = false,
    initialValue: Float,
    onValueChanged: (Float) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialValue.toString()) }

    if (displayNumberInput) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Enter Value") },
            text = {
                TextField(
                    value = text,
                    onValueChange = { newValue ->
                        text = newValue.filter { it.isDigit() || it == '.' } // This permits only integers and decimal points TODO: Only allow one decimal point
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // When done is pressed, convert the text to a Float and pass it to the callback
                            text.toFloatOrNull()?.let { onValueChanged(it) }
                            onDismiss()
                        }
                    )
                )
            },
            confirmButton = {
                Button(onClick = {
                    text.toFloatOrNull()?.let { onValueChanged(it) }
                    onDismiss()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }

}

@Preview(showBackground = true, widthDp = 620, heightDp = 320)
@Composable
fun NumberInputPreview() {
    NumberInput(initialValue= 0.0f, onValueChanged= {}, onDismiss={} )
}
