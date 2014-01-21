package MenuButtons;



public class RadioGroup {

	private ToggleButton button0;
	private ToggleButton button1;
	private ToggleButton button2;
	private int buttonPressed = 0;
	
	public RadioGroup(ToggleButton button0, ToggleButton button1, ToggleButton button2){
		this.button0 = button0;
		this.button1 = button1;
		this.button2 = button2;
		
	}
	
	public int update(){
		if(button0.getPressed()){
			buttonPressed = 0;
		}else if(button1.getPressed()){
			buttonPressed = 1;
		}else if(button2.getPressed()){
			buttonPressed = 2;
		}
		
		return buttonPressed;
	}
	
	public void setButton(int index){
		switch(index){
		case 0:{ 
			button0.setPressed(true);
			button1.setPressed(false);
			button2.setPressed(false);
			break;}
		case 1:{ 
			button0.setPressed(false);
			button1.setPressed(true);
			button2.setPressed(false);
			break;}
		case 2:{ 
			button0.setPressed(false);
			button1.setPressed(false);
			button2.setPressed(true);
			break;}
		}
	}
	
}
