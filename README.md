# Simon-Says-Android

An Android App recreating the Simon Says game. Uses MediaPipe to run the Gemma 2b LLM on device.

Read the accompanying blog post below: 

- [Playing Simon Says with Gemma and MediaPipe](https://www.darrylbayliss.net/playing-simon-says-with-gemma-and-mediapipe/)

# Installation

1. Clone the repo

2. Go to [Kaggle](https://www.kaggle.com/models/google/gemma), sign up and accept the Gemma T&C's. Download the `gemma-2b-it-cpu` version of the models

3. Using Device Explorer, import the Gemma model onto your device in the path specified in `AppModule.kt`

4. Run the app.
