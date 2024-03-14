package com.darrylbayliss.simonsays.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import com.darrylbayliss.simonsays.data.ImageClassificationState
import com.google.mediapipe.framework.image.BitmapImageBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val startSimonSays: StartSimonSays,
    private val sendMessageToSimon: SendMessageToSimon,
    private val sendImageToSimon: SendImageToSimon,
    private val requestNewTaskFromSimon: RequestNewTaskFromSimon
) : ViewModel() {
    val messages: StateFlow<List<Message>>
        get() = _messages

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(
        emptyList()
    )

    fun startGame() {
        val message = startSimonSays()
        val list = _messages.value.toMutableList()
        list += message

        _messages.update {
            list
        }
    }

    fun sendMessage(text: String) {
        val newMessage = Message(
            text = text,
            isFromMe = true
        )

        val list = _messages.value.toMutableList()
        list += newMessage

        _messages.update {
            list
        }

        val messageFromSimon = sendMessageToSimon(message = newMessage)
        list += messageFromSimon

        _messages.update {
            list
        }
    }

    fun sendPhoto(imageBitmap: ImageBitmap) {
        val newMessage = Message(
            image = imageBitmap,
            isFromMe = true
        )

        val list = _messages.value.toMutableList()
        list += newMessage

        _messages.update {
            list
        }

        val imageClassificationState =
            sendImageToSimon(image = BitmapImageBuilder(imageBitmap.asAndroidBitmap()).build())

        val message = when (imageClassificationState) {
            is ImageClassificationState.NotRecognised -> {
                imageClassificationState.message
            }

            is ImageClassificationState.Recognised -> {
                imageClassificationState.message
            }
        }

        val newMessageFromSimon = Message(
            text = message,
            isFromMe = true
        )

        list += newMessageFromSimon

        _messages.update {
            list
        }

        if (imageClassificationState is ImageClassificationState.NotRecognised) {
            val newTask = requestNewTaskFromSimon()

            val newTaskFromSimon = Message(
                text = newTask,
                isFromMe = true
            )

            list += newTaskFromSimon

            _messages.update {
                list
            }
        }
    }
}

data class Message(val text: String = "", val image: ImageBitmap? = null, val isFromMe: Boolean)
