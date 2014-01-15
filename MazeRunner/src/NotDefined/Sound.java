package NotDefined;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
/**
 * Class to store and load audioclips
 * @author Tark
 *
 */
public class Sound {

	private Clip clip;
	private AudioInputStream audioInputStream;
	/**
	 * Stores given audio file as a playable clip
	 * @param filepath path of audio file
	 */
	public Sound(String filepath){
	try {
		audioInputStream = AudioSystem.getAudioInputStream(
		    new File(filepath));
		clip = AudioSystem.getClip();
	} catch (UnsupportedAudioFileException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	/**
	 * Plays a certain sound in a continious loop
	 */
	public void playloop(){
		try {
			this.thisClip().open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.thisClip().loop(Clip.LOOP_CONTINUOUSLY);
	}
	/**
	 * Stops  a certain audio clip from playing
	 */
	public void stop(){
		this.thisClip().stop();
	}
	/**
	 * 
	 * @return returns clip from Sound 
	 */
	public Clip thisClip(){
		return clip;
	}
	/**
	 * plays Sound once
	 */
	public void play(){
		try {
			this.thisClip().open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.thisClip().loop(0);
	}
	/**
	 * Sets the gain of a Sound to given value
	 * @param Gain Gain to set
	 */
	public void setGain(float Gain){
		FloatControl gainControl = 
			    (FloatControl) this.thisClip().getControl(FloatControl.Type.MASTER_GAIN);
		float gain = gainControl.getValue();	
		if(Gain<6&&Gain>-80){
			gainControl.setValue(Gain);
		}
	}
	/**
	 * increased gain by 1
	 */
	public void IncreaseGain(){
		FloatControl gainControl = 
			    (FloatControl) this.thisClip().getControl(FloatControl.Type.MASTER_GAIN);
		float gain = gainControl.getValue();	
		
		if(gainControl.getValue() <= 5){
			gainControl.setValue(gainControl.getValue() + 1.0f);
		}
		
		System.out.println(gainControl.getValue());
	}
	
}
