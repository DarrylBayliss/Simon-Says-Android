package com.darrylbayliss.simonsays.domain

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val startSimonSays: StartSimonSays,
    private val sendMessageToSimon: SendMessageToSimon,
    private val sendImageToSimon: SendImageToSimon
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

    fun sendImage() {

    }
}

data class Message(val text: String, val isFromMe: Boolean)
