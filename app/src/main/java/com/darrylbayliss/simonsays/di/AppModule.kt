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
                .setModelPath("/data/local/tmp/llm/gemma-2b-it-cpu-int8.bin")
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
            .setScoreThreshold(80.0f)
            .setMaxResults(1)
            .build()
        return ImageClassifier.createFromOptions(context, options)
    }
}
