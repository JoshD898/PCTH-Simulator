package com.example.pharmacologyapp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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