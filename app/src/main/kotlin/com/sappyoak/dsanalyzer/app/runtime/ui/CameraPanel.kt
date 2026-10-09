package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot

@Composable
internal fun CameraPanel(camera: CameraSnapshot?) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text("Camera", style = MaterialTheme.typography.titleSmall)

        if (camera == null) {
            Text("No camera, so no world is loaded", style = MaterialTheme.typography.bodySmall)
            return@Column
        }

        Field("Position", camera.position.describe())
        Field("Facing", camera.orientation.describe())
        Field("Pitching toward", "%.3f rad".format(camera.targetPitch))

        val frustum = camera.frustum
        Field("Field of view", "%.3f rad (%.1f deg vertical)".format(frustum.fovRadians, frustum.fovRadians * DEGREES))
        Field("Aspect", "%.4f".format(frustum.aspect))
        Field("Clip planes", "%.4f to %.1f".format(frustum.near, frustum.far))

        Field("Transform position", camera.transform.r3.xyz.describe())
        Field(
            "Basis",
            if (camera.transform.isOrthonormal()) "orthonormal" else "NOT orthonormal - wrong offset?"
        )
    }
}

private const val DEGREES = 180f / Math.PI.toFloat()