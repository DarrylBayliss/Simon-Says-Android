package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository
import com.darrylbayliss.simonsays.presentation.Message
import javax.inject.Inject

class StartSimonSays @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    suspend operator fun invoke(): Message {
        return mediapipeRepository.startGame()
    }
}
