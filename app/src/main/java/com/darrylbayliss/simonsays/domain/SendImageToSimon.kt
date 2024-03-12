package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.MediapipeRepository
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.ClassificationResult
import javax.inject.Inject

class SendImageToSimon @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(image: MPImage): ClassificationResult = mediapipeRepository.checkImage(image = image)
}
