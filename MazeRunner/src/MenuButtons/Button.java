package MenuButtons;
import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class Button {

	private GL gl;
	private int screenWidth;
	private int screenHeight;
	private int index;
	private String string;
	private float x;
	private float y	;
	private float buttonHeight;
	private float buttonWidth;
	private float buttonSpace;
	

	
	public Button(GL gl, int screenWidth, int screenHeight, int index, String string){
		
	
		
		this.gl = gl;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.index = index;
		this.string = string;
		
		this.buttonHeight = this.screenHeight / 15.0f;
		this.buttonWidth = this.screenWidth / 2.5f;
		this.buttonSpace = this.buttonHeight + buttonHeight / 4.0f;
	
		x = screenWidth / 2.0f;
		y = screenHeight / 2.0f - (this.index -2)*this.buttonSpace;
		
		
		drawButton();
		drawText();
	}
	
	public void drawButton(){
	
		
		gl.glColor3f(1f, 0.647f, 0f);
		rectOnScreen(gl, x, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x, y, buttonHeight*0.75f, buttonWidth*0.95f);
	
	}
	public void drawNeg(){
		
		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(1f, 0.647f, 0f);
		rectOnScreen(gl, x, y, buttonHeight*0.75f, buttonWidth*0.95f);
	}
	public void drawPressed(){
		
		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x, y, buttonHeight,buttonWidth);
	

		gl.glColor3f(0.8f, 0.522f, 0f);
		rectOnScreen(gl, x, y, buttonHeight*0.75f, buttonWidth*0.95f);
		
	}
	
	public void drawText()
	{
		int x = Math.round(this.x);
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
		
		if((x < this.x + (buttonWidth / 2.0f)) && (x > this.x - (buttonWidth / 2.0f))){
			if((y < this.y + buttonHeight / 2.0f + 2 * (this.index -2)*this.buttonSpace) && (y > this.y - buttonHeight/2.0f+ 2 * (this.index -2)*this.buttonSpace)){
				inside = true;
			}
		}
		return inside;
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
