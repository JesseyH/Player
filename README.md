#Summary
Player.java allows synthesizing texts to speech, play SFX, and allow interactivity using simple command within a simple text file. Player.java helps visually impaired user to hear and feel word using braille controlled by simulator API.
#Examples

"<TTS>,HELLO" - Command to synthesize text into speech

"<SFX>,./resources/beep.wav" - Command to play SFX located at “./resources/beep.wav”

"<DISPLAY>,Hey" - sets braille cell state to display Hey

#How to run 

Navigate to /src/ folder and run commands:

Java Player.java, then javac Player

#Tests

Please refer to PlayerTest.java file in repository

