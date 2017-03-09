# Summary
`Player.java` allows synthesizing texts to speech, play SFX, and interactivity using simple command within a simple text file. `Player.java` can be used (but not limited to) to help visually impaired user to hear and feel word using braille controlled by simulator API.
# Examples

`\<TTS\>,HELLO` - Command to synthesize text into speech

`"\<SFX\>,./resources/beep.wav"` - Command to play SFX located at “./resources/beep.wav”

`"\<DISPLAY\>,Hey"` - sets braille cell state to display `Hey`

# How to run 
1. Import project into `eclipse` and run `[recommended]`
    - Alternatively:
      - Navigate to `/src/` folder and run commands:
      - Java Player.java, then javac Player

2. Provide filName.txt file containing texts to be read and commands using input to scanner

# Tests

Please refer to `PlayerTest.java` file in repository to see testcases

