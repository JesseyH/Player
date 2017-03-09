import java.io.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javax.sound.sampled.*;

/**
 * Speak class manages the text to speach engine as well
 * as playing sounds.
 * @author Group 6
 *
 */
public class Speak {
	private static final String VOICENAME_kevin = "kevin";
	private static final int BUFFER_SIZE = 128000;
	private static SourceDataLine line;
	private static Voice voice;
	
	/**
	 * Uses the text to speech engine on the passed text.
	 * @param text The text that will be spoken.
	 */
	public static void textToSpeech(String text) {
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice(VOICENAME_kevin);
		voice.allocate();
		voice.speak(text);
		voice.deallocate();
	}
	
	/**
	 * Plays a sound a file.
	 * @param fileName The sound file to play.
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
	
}