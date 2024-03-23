package com.darrylbayliss.simonsays.data

import com.darrylbayliss.simonsays.domain.Message
import com.google.mediapipe.framework.image.MPImage
import javax.inject.Inject

sealed class ImageClassificationState {
    data class NotRecognised(val message: String) : ImageClassificationState()
    data class Recognised(val message: String) : ImageClassificationState()
}

class MediapipeRepository @Inject constructor(private val mediapipeLLMDataSource: MediapipeLLMDataSource) {
    suspend fun startGame(): Message {
        val message = mediapipeLLMDataSource.start()
        return Message(
            text = message,
            isFromMe = false
        )
    }

    suspend fun sendMessage(message: Message): String {
        return mediapipeLLMDataSource.sendMessage()
    }

    fun checkImage(image: MPImage): ImageClassificationState {
        val classificationResult = mediapipeLLMDataSource.classifyImage(image = image)

        val category = classificationResult.classifications().first().categories().firstOrNull()

        return if (category == null) {
            ImageClassificationState.NotRecognised("I don't recognise that. 🤔 Let's try another task.")
        } else if (category.score() >= 75.0f) {
            ImageClassificationState.Recognised("That's a ${category.categoryName()} all right!")
        } else {
            ImageClassificationState.NotRecognised("That doesn't look right to me. Let's try another task.")
        }
    }
    fun requestNewTask(): String = mediapipeLLMDataSource.requestNewTask()
}
