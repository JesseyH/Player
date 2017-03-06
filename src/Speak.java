import java.io.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javax.sound.sampled.*;

public class Speak {
	private static final String VOICENAME_kevin = "kevin";
	private static final int BUFFER_SIZE = 128000;
	private static SourceDataLine line;
	private static Voice voice;
	
	/**
	 * 
	 * @param text
	 */
	public static void textToSpeech(String text) {
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice(VOICENAME_kevin);
		voice.allocate();
		voice.speak(text);
		voice.deallocate();
	}
	
	/**
	 * 
	 * @param fileName
	 */
	public static void playSound(String fileName){
        try {
			File file = new File(fileName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = audioStream.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(format);    
			line.start();
			int read = 0;
			byte[] data = new byte[BUFFER_SIZE];
			while ((read = audioStream.read(data, 0, data.length)) != -1) {
			    if (read >= 0) {
			        line.write(data, 0, read);
			    }
			}

			line.drain();
			line.close();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	/*
	 * 
	 */
	public static boolean isTextToSpeechActive() {
		return voice.isLoaded();
	}
	
	/*
	 * 
	 */
	public static boolean isPlaySoundActive() {
		return line.isOpen();
	}
	
	//Example cases
	public static void main(String[] args) {
		Speak.playSound("resources/beep.wav");
		Speak.textToSpeech("It's Alive");
	}
}


