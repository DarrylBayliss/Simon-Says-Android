package com.darrylbayliss.simonsays.domain

import com.darrylbayliss.simonsays.data.ImageClassificationState
import com.darrylbayliss.simonsays.data.MediapipeRepository
import com.google.mediapipe.framework.image.MPImage
import javax.inject.Inject

class SendImageToSimon @Inject constructor(private val mediapipeRepository: MediapipeRepository) {
    operator fun invoke(image: MPImage): ImageClassificationState = mediapipeRepository.checkImage(image = image)
}
