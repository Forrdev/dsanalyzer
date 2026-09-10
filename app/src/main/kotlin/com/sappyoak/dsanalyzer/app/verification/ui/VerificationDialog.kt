package com.sappyoak.dsanalyzer.app.verification.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.app.verification.VerificationRecord

import com.sappyoak.dsanalyzer.app.verification.VerificationStatus
import com.sappyoak.dsanalyzer.game.Installation

private const val SAMPLE_SIZE = 10

@Composable
public fun VerificationDialog(
    installation: Installation,
    status: VerificationStatus?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Verify ${installation.build.edition.name}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(installation.root.toString())
                Body(status)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

@Composable
private fun Body(status: VerificationStatus?) {
    when (status) {
        null -> Text("Not checked yet")

        VerificationStatus.Running -> {
            CircularProgressIndicator()
            Text("Checking")
        }

        VerificationStatus.Unsupported -> Text("This edition's files cannot be listed yet")

        is VerificationStatus.Failed -> Text("The check could not be completed: ${status.reason}")
        is VerificationStatus.Complete -> Summary(status.record)
    }
}

@Composable
private fun Summary(record: VerificationRecord) {
    if (record.missing.isEmpty()) {
        Text("Every expected file is present")
    } else {
        Text("${record.missing.size} expected files are missing")
        Text(record.missing.take(SAMPLE_SIZE).joinToString("\n"))
    }

    if (record.unidentified.isNotEmpty()) {
        Text(
            "${record.unidentified.size} entries could not be identified. " +
            "This could mean the installation has been modified, in which case the tool may not " +
            "behave as expected"
        )
    }
}