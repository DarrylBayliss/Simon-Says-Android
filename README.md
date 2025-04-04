# Simon-Says-Android

An Android App recreating the Simon Says game. Uses MediaPipe to run the Gemma3 1b LLM on device.  📣 🦾

<p align="center">
  <img src="/app-demo.gif" width="400" height="800" />
</p>

Read the accompanying blog post below to learn about how MediaPipe works and how to run Gemma on device: 

- [Playing Simon Says with Gemma and MediaPipe](https://www.darrylbayliss.net/playing-simon-says-with-gemma-and-mediapipe/)

# Installation

1. Clone the repo

2. Go to [Kaggle](https://www.kaggle.com/models/google/gemma), sign up and accept the Gemma T&C's. Download the `gemma3-1b-it-int4.task` version of the model under the TensorFlow Lite tab.

3. Using Device Explorer, import the Gemma model onto your device in the path specified in `AppModule.kt`

4. Run the app.

# Other Apps

Enjoyed this app? Check out the [iOS](https://github.com/DarrylBayliss/Simon-Says-iOS) version. 📱
