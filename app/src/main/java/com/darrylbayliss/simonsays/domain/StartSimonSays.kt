package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository
import javax.inject.Inject

class StartSimonSays @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(): Message {
        return mediapipeRepository.startGame()
    }
}
