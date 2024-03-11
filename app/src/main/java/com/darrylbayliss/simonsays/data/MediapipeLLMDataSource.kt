package com.darrylbayliss.simonsays.data

import com.google.mediapipe.tasks.genai.llminference.LlmInference
import javax.inject.Inject


private const val StartPrompt = """
    You are Simon in a game of Simon Says. Your goal is to ask the player to perform tasks as if only you and the player are in the game.
    You can ask the player to perform a variety of tasks, suitable for indoors.
    You can ask the player to take photos of objects, as a task.
    It is imperative you do not ask them to do anything that is potentially harmful or unlawful.
    Begin the game by being friendly. Then ask them a task and await their response.
"""

class MediapipeLLMDataSource @Inject constructor(private val llmInference: LlmInference) {

    fun start(): String = llmInference.generateResponse(StartPrompt)

    fun sendMessage(message: String): String = llmInference.generateResponse(message)
}
