package com.example.pharmacologyapp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pharmacologyapp.classes.Drug

/**
 * Displays a message to the user telling them if they got the answer right or wrong
 *
 * @param modifier Modifier to be applied
 * @param showAlert Boolean that controls if the alert is shown
 * @param isAnswerCorrect True if the answer was correct
 * @param onDismiss Function that closes the alert
 */
@Composable
fun AnswerMessage(
    modifier: Modifier = Modifier,
    showAlert: Boolean = false,
    isAnswerCorrect: Boolean,
    onDismiss: () -> Unit
) {
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = if (isAnswerCorrect) "Correct!" else "Wrong Answer")
            },
            text = {
                Text(
                    text = if (isAnswerCorrect) {
                        "Congratulations! You will ace you next exam :)"
                    } else {
                        "Try again!"
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("Close")
                }
            },
            modifier = modifier
        )
    }
}

/**
 * Displays a message to the user confirming that the unknown has been randomized, and giving the answers for the previous experiment
 *
 * @param modifier Modifier to be applied
 * @param showAlert Boolean that controls if the alert is shown
 * @param oldUnknown The old unknown drug
 * @param oldConcentration The old unknown concentration
 * @param onDismiss Function that closes the alert
 */
@Composable
fun RandomizeMessage(
    modifier: Modifier = Modifier,
    showAlert: Boolean = false,
    oldUnknown: Drug,
    oldConcentration: Float,
    onDismiss: () -> Unit
) {
    if (showAlert) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "The unknown has been randomized!")
            },
            text = {
                Text(
                    text = """
                        Previous experiment answers:
                        Unknown identity: ${oldUnknown.name}
                        Unknown concentration: ${"%.2f".format(oldConcentration)} uM
                    """.trimIndent()
                )
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            },
            modifier = modifier
        )
    }
}