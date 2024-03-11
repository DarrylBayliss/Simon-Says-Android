package com.darrylbayliss.simonsays.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.darrylbayliss.simonsays.R
import com.darrylbayliss.simonsays.domain.Message
import com.darrylbayliss.simonsays.domain.PlayViewModel
import com.darrylbayliss.simonsays.ui.theme.PurpleGrey80
import com.darrylbayliss.simonsays.ui.theme.SimonSaysTheme


private const val StartGameKey = "StartGame"

@Composable
fun PlayScreen(viewModel: PlayViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (messagesRef, chatBoxRef) = createRefs()

        val messages = viewModel.messages.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messagesRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(chatBoxRef.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(items = messages.value) { item ->
                ChatItem(item)
            }
        }

        ChatBox(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chatBoxRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            onSendMessageClicked = { message ->

            },
            onTakePhotoClicked = {

            }
        )
    }

//    LaunchedEffect(key1 = StartGameKey) {
//        viewModel.startGame()
//    }
}

@Composable
fun ChatItem(message: Message) {
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
            Text(text = message.text)
        }
    }
}

@Composable
fun ChatBox(
    modifier: Modifier,
    onSendMessageClicked: (String) -> Unit,
    onTakePhotoClicked: () -> Unit
) {

    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = modifier, horizontalArrangement = Arrangement.Absolute.SpaceEvenly) {
        TextField(
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            placeholder = {
                Text(text = "Type something")
            }
        )
        IconButton(onClick = { onSendMessageClicked(chatBoxValue.text) }) {
            Icon(painter = painterResource(id = R.drawable.send), contentDescription = null)
        }
        IconButton(onClick = { onTakePhotoClicked() }) {
            Icon(painter = painterResource(id = R.drawable.photo_camera), contentDescription = null)
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
