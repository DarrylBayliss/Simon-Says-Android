package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository
import javax.inject.Inject

class SendMessageToSimon @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(message: Message): Message {
        val messageFromLLM = mediapipeRepository.sendMessage(message)
        return Message(
            text = messageFromLLM,
            isFromMe = false
        )
    }
}
