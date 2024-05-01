package com.darrylbayliss.simonsays.data

import android.util.Log
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.ClassificationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val SimonSaysPrompt = """
    You are a Simon in a game of Simon Says.
    
    For every task you give, you must prefix it with the words "Simon says".
    
    You must not ask the player to do anything that is dangerous, unethical or unlawful.
"""

private const val MovePrompt = SimonSaysPrompt + """
    Ask the player to move to a different position.
"""

private const val TouchBodyPartPrompt = SimonSaysPrompt + """
    Ask the player to touch an unoffensive body part.
"""

private const val SingASongPrompt = SimonSaysPrompt + """
    Ask the player to sing a song of their choice.
"""

private const val TakePhotoPrompt = SimonSaysPrompt + """
   Ask the player to take a photo of an object.
"""

private const val MakeANoisePrompt = SimonSaysPrompt + """
    Ask the player to make a loud noise.
"""

class MediapipeLLMDataSource @Inject constructor(
    private val llmInference: LlmInference,
    private val imageClassifier: ImageClassifier
) {

    private val prompts = listOf(
        MovePrompt,
        TouchBodyPartPrompt,
        SingASongPrompt,
        TakePhotoPrompt,
        MakeANoisePrompt
    )

    suspend fun start(): String {
        return withContext(Dispatchers.IO) {
            Log.i(
                MediapipeLLMDataSource::class.java.simpleName,
                "Starting Simon says with the following prompt: $SimonSaysPrompt"
            )
            llmInference.generateResponse(SimonSaysPrompt)
        }
    }

    suspend fun sendMessage(): String {
        return withContext(Dispatchers.IO) {
            val prompt = prompts.random()
            Log.i(
                MediapipeLLMDataSource::class.java.simpleName,
                "Passing LLM the following prompt: $prompt"
            )
            llmInference.generateResponse(prompt)
        }
    }

    fun classifyImage(image: MPImage): ClassificationResult =
        imageClassifier.classify(image).classificationResult()

    fun requestNewTask(): String {
        val prompt = prompts.random()
        Log.i(
            MediapipeLLMDataSource::class.java.simpleName,
            "Passing LLM the following prompt: $prompt"
        )
        return llmInference.generateResponse(prompt)
    }
}
