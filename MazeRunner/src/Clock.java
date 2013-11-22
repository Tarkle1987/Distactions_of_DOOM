import javax.media.opengl.GL;


public class Clock {

	protected int seconds;
	protected int minutes;
	private Digit[] clock;
	
	public Clock(){
		this.seconds = 0;
		this.minutes = 0;
		
		clock = new Digit[4];
		
		clock[0] = new Digit();
		clock[1] = new Digit();
		clock[2] = new Digit();
		clock[3] = new Digit();
	}
	
	public void display(GL gl, float screenWidth, float screenHeight){
		update();
		
		clock[0].display(gl, screenWidth - 40, screenHeight - 15);
		clock[1].display(gl, screenWidth - 40 - clock[1].size*33, screenHeight - 15);
		clock[2].display(gl, screenWidth - 40 - clock[2].size*80, screenHeight - 15);
		clock[3].display(gl, screenWidth - 40 - clock[3].size*113, screenHeight - 15);
	}

	private void update() {
		if(seconds > 60){
			minutes = minutes + 1;
			seconds = seconds - 60;
		}
		
		clock[0].set(seconds%10);
		clock[1].set(seconds/10);
		clock[2].set(minutes%10);
		clock[3].set(minutes/10);
	}
	
}
