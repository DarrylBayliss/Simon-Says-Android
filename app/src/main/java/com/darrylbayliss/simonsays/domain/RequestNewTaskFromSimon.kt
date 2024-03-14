package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository
import javax.inject.Inject

class RequestNewTaskFromSimon @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(): String = mediapipeRepository.requestNewTask()
}
