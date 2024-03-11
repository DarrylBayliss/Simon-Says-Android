package com.darrylbayliss.simonsays.data

import com.darrylbayliss.simonsays.domain.Message
import javax.inject.Inject

class MediapipeRepository @Inject constructor(private val mediapipeLLMDataSource: MediapipeLLMDataSource) {
    fun startGame(): Message {
        val message = mediapipeLLMDataSource.start()
        return Message(
            text = message,
            isFromMe = false
        )
    }

    fun sendMessage(message: Message): String {
        return mediapipeLLMDataSource.sendMessage(message = message.text)
    }
}
