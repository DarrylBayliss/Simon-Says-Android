@file:OptIn(ExperimentalPermissionsApi::class)

package com.darrylbayliss.simonsays.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.darrylbayliss.simonsays.R
import com.darrylbayliss.simonsays.domain.Message
import com.darrylbayliss.simonsays.domain.PlayViewModel
import com.darrylbayliss.simonsays.ui.theme.PurpleGrey80
import com.darrylbayliss.simonsays.ui.theme.SimonSaysTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


private const val StartGameKey = "StartGame"

@Serializable
object Play

@Composable
fun PlayScreen(viewModel: PlayViewModel) {

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                viewModel.sendPhoto(
                    bitmap.asImageBitmap()
                )
            }
        }

    val cameraPermissions = rememberPermissionState(
        android.Manifest.permission.CAMERA
    ) {
        cameraLauncher.launch()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val messages by viewModel.messages.collectAsState()

        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        if (messages.isNotEmpty()) {
            LaunchedEffect(key1 = messages.size) {
                lazyListState.animateScrollToItem(index = messages.lastIndex)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
            state = lazyListState
        ) {
            items(items = messages) { item ->
                ChatItem(message = item)
            }
        }

        val focusManager = LocalFocusManager.current

        ChatBox(
            modifier = Modifier
                .imePadding()
                .fillMaxWidth(),
            onTextFieldClicked = {
                coroutineScope.launch {
                    lazyListState.scrollToItem(index = messages.lastIndex)
                }
            },
            onSendMessageClicked = { message ->
                viewModel.sendMessage(message)
            },
            onTakePhotoClicked = {
                cameraLauncher.launch()
                focusManager.clearFocus()
            },
            cameraPermissions = cameraPermissions,
        )
    }

    LaunchedEffect(key1 = StartGameKey) {
        viewModel.startGame()
    }
}

@Composable
fun ChatItem(
    message: Message
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .background(PurpleGrey80)
                .padding(16.dp)
        ) {
            if (message.text.isNotEmpty()) {
                Text(text = message.text)
            } else if (message.image != null) {
                Image(painter = BitmapPainter(image = message.image), contentDescription = "")
            }
        }
    }
}

@Composable
fun ChatBox(
    modifier: Modifier,
    onSendMessageClicked: (String) -> Unit,
    onTakePhotoClicked: () -> Unit,
    onTextFieldClicked: () -> Unit,
    cameraPermissions: PermissionState
) {

    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        onTextFieldClicked()
    }

    Row(
        modifier = modifier,
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            placeholder = {
                Text(text = "Type something")
            },
            interactionSource = interactionSource
        )
        IconButton(onClick = {
            onSendMessageClicked(chatBoxValue.text)
            chatBoxValue = TextFieldValue("")
        }) {
            Icon(painter = painterResource(id = R.drawable.send), contentDescription = null)
        }
        IconButton(onClick = {
            if (cameraPermissions.status.isGranted) {
                onTakePhotoClicked()
            } else {
                cameraPermissions.launchPermissionRequest()
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.photo_camera),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayPreview() {
    SimonSaysTheme {
        PlayScreen(hiltViewModel<PlayViewModel>())
    }
}
