package com.darrylbayliss.simonsays.di

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
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
                .setModelPath("/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin")
                .build()
        )
    }
}
