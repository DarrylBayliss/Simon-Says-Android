package com.darrylbayliss.simonsays.di

import android.content.Context
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier
import com.google.mediapipe.tasks.vision.imageclassifier.ImageClassifier.ImageClassifierOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideLlmInference(@ApplicationContext context: Context): LlmInference {
        return LlmInference.createFromOptions(
            context,
            LlmInference.LlmInferenceOptions.builder()
                .setModelPath("/data/local/tmp/slm/gemma3-1B-it-int4.task")
                .setPreferredBackend(LlmInference.Backend.CPU) // Change to GPU if you have a GPU powered device.
                .setMaxTopK(64)
                .build()
        )
    }

    @Provides
    fun provideImageClassifier(@ApplicationContext context: Context): ImageClassifier {
        val options = ImageClassifierOptions.builder()
            .setBaseOptions(
                BaseOptions.builder().setModelAssetPath("efficientnet_lite2.tflite").build()
            )
            .setRunningMode(RunningMode.IMAGE)
            .setScoreThreshold(30.0f)
            .setMaxResults(10)
            .build()
        return ImageClassifier.createFromOptions(context, options)
    }
}
