package com.darrylbayliss.simonsays.data

import com.darrylbayliss.simonsays.domain.Message
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.ClassificationResult
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
    fun checkImage(image: MPImage): ClassificationResult {
        return mediapipeLLMDataSource.classifyImage(image = image)
    }
}
