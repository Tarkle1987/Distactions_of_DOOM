package MenuButtons;
import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class ToggleButton {

	private GL gl;
	private String string;
	private float x;
	private float y	;
	private float buttonWidth;
	private float buttonHeight;
	private float buttonSpace;
	private int index;

	private boolean pressed;
	
	public ToggleButton(GL gl, float x, float y, float buttonWidth, float buttonHeight, int index, String string){
		
		this.index = index;
		this.string = string;
		this.gl = gl;
		this.x = x;
		this.y = y;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.buttonSpace = this.buttonWidth + this.buttonWidth/6.0f;
		
	}
	
	public float getSpace(){
		return buttonSpace;
	}
	public float getHeight(){
		return buttonHeight;
	}
	public float getWidth(){
		return buttonWidth;
	}
	
	public void reduceWidth(int amount){
		this.buttonWidth = this.buttonHeight / amount;
		this.buttonSpace = this.buttonWidth + this.buttonHeight/4.0f;
	}
	
	public void draw(){
		
		gl.glColor3f(1f, 0.647f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight*0.75f, buttonWidth*0.75f);
		
	
		drawText();
	}
	
	public void drawText()
	{
		int x = Math.round(this.x + index*buttonSpace);
		int y = Math.round(this.y);
		
		int length = string.length();
		
		GLUT glut = new GLUT();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glRasterPos2i(x - 17- 6*length/2, y - 7); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, string.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
		}
	}
	

	public boolean CursorInButton(int x, int y){
		boolean inside = false;
		
		
		if((x < this.x + (buttonWidth / 2.0f)+ index*buttonSpace) && (x > this.x - (buttonWidth / 2.0f)+ index*buttonSpace)){
			if((y < this.y + buttonHeight / 2.0f) && (y > this.y - buttonHeight/2.0f)){
				inside = true;
			}
		}
		return inside;
	}
	public void drawNeg(){
		
		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(1f, 0.647f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight*0.75f, buttonWidth*0.75f);
	
		drawText();
	}
	public void drawPressed(){
		
		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x + index*buttonSpace, y, buttonHeight*0.75f, buttonWidth*0.75f);
	
		drawText();
	}
	public void NegIfIn(int X, int Y){
		
		if(CursorInButton(X,Y)){
			drawNeg();
			drawText();
		}
	}
	public void PresIfIn(int X, int Y){
		
		if(CursorInButton(X,Y)){
			drawPressed();
			drawText();
		}
	}
	public void setPressed(boolean pressed){
		this.pressed = pressed;
	}
	public boolean getPressed(){
		return pressed;
	}
	
	private void rectOnScreen(GL gl, float x, float y, float height, float width){
		height = height / 2.0f;
		width = width / 2.0f;
	
		gl.glBegin(GL.GL_QUADS);
		
		gl.glVertex2f(x - width,y + height);
		gl.glVertex2f(x - width,y - height);
		gl.glVertex2f(x + width, y - height);
		gl.glVertex2f(x + width,y + height);
		gl.glEnd();
	}
}