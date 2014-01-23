package MenuButtons;

public class ToggleButton extends Knop {

	private boolean pressed = false;
	
	public ToggleButton(int x1, int y1, int x2, int y2){
		super(x1,y1,x2,y2);
	}

	public void setPressed(boolean pressed){
		this.pressed = pressed;
	}
	public boolean getPressed(){
		return pressed;
	}

}