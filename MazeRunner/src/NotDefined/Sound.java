package NotDefined;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Sound {

	private Clip clip;
	private AudioInputStream audioInputStream;
	
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
	
	public Clip thisClip(){
		return clip;
	}
	
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
	
	public void setGain(float Gain){
		FloatControl gainControl = 
			    (FloatControl) this.thisClip().getControl(FloatControl.Type.MASTER_GAIN);
		float gain = gainControl.getValue();	
		gainControl.setValue(Gain);
	}
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
