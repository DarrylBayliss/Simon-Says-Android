package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository

class SendMessageToSimon(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(message: Message) {
        mediapipeRepository.sendMessage(message)
    }
}
