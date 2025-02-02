package com.ahmed_nezhi.goal_presentation.component

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.ahmed_nezhi.common.utils.createImageUri
import com.ahmed_nezhi.goal_presentation.R
import com.ahmed_nezhi.goal_presentation.details.viewmodel.GoalDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalProgressBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    scope: CoroutineScope,
    viewModel: GoalDetailsViewModel,
    goalId: Int
) {
    // State for thoughts and image URI
    var thoughts by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var displayPreview by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Camera and permission launchers
    val cameraLauncher = rememberCameraLauncher(imageUri) { success ->
        if (success) {
            displayPreview = true
        }
    }
    val permissionLauncher = rememberPermissionLauncher(context) {
        imageUri = createImageUri(context)
        imageUri?.let { cameraLauncher.launch(it) }
    }

    // Bottom sheet content
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            BottomSheetTitle()

            // Thoughts TextField
            ThoughtsTextField(thoughts) { thoughts = it }

            ImagePreviewAndCaptureButton(
                imageUri = imageUri,
                onCaptureImage = {
                    checkCameraPermission(context, permissionLauncher) {
                        imageUri = createImageUri(context)
                        imageUri?.let { cameraLauncher.launch(it) }
                    }
                },
                displayPreview = displayPreview
            )

            // Save Progress Button
            SaveProgressButton(
                isEnabled = thoughts.isNotEmpty() && imageUri != null,
                onSaveProgress = {
                    viewModel.addProgress(goalId, thoughts, imageUri ?: Uri.EMPTY)
                    scope.launch {
                        sheetState.hide()
                        onDismissRequest()
                    }
                }
            )
        }
    }
}

@Composable
private fun BottomSheetTitle() {
    Text(
        text = stringResource(R.string.add_goal_progress),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ThoughtsTextField(
    thoughts: String,
    onThoughtsChange: (String) -> Unit
) {
    OutlinedTextField(
        value = thoughts,
        onValueChange = onThoughtsChange,
        label = { Text("Thoughts") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = false
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ImagePreviewAndCaptureButton(
    imageUri: Uri?,
    onCaptureImage: () -> Unit,
    displayPreview: Boolean
) {
    if (displayPreview) {
        imageUri?.let { uri ->
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = uri,
                    contentDescription = "Captured image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                )
            }
        }
    }
    Button(
        onClick = onCaptureImage,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Upload Image from Camera")
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SaveProgressButton(
    isEnabled: Boolean,
    onSaveProgress: () -> Unit
) {
    Button(
        onClick = onSaveProgress,
        enabled = isEnabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.save_progress))
    }
}

private fun checkCameraPermission(
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>,
    onPermissionGranted: () -> Unit
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    )
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        onPermissionGranted()
    } else {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

@Composable
private fun rememberCameraLauncher(
    imageUri: Uri?,
    onImageCaptured: (Boolean) -> Unit
): ActivityResultLauncher<Uri> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = onImageCaptured
    )
}

@Composable
private fun rememberPermissionLauncher(
    context: Context,
    onPermissionGranted: () -> Unit
): ActivityResultLauncher<String> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }
}