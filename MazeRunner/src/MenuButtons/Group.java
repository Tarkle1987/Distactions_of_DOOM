package MenuButtons;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class Group {

	private GL gl;
	private int screenWidth;
	private int screenHeight;
	private String string;
	private float x;
	private float y;
	private int index;
	private float groupHeight;
	private float groupWidth;
	private float groupSpace;
	private float buttonWidth;
	private float buttonHeight;
	private int amount;
	private ArrayList<ToggleButton> list;
	
	public Group(GL gl, int screenWidth, int screenHeight, int index, String string){
		
		this.string = string;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.gl = gl;
		this.groupHeight = 2*(screenHeight / 15.0f);
		this.groupWidth = screenWidth / 2.5f;
		this.groupSpace = groupHeight + groupHeight / 16.0f;
		
		this.index = index;
		
		this.x = screenWidth /2.0f;
		this.y = screenHeight / 2.0f - (this.index -2)*this.groupSpace;
		
		this.buttonHeight = (float)(this.groupHeight * 0.65);
		this.buttonWidth =  (float)(this.groupWidth * 0.2833);
		
		this.list = new ArrayList<ToggleButton>();
		this.amount = 0;
	}
	
	public int drawGroup(int CurrentX, int CurrentY, int PressedX, int PressedY, int radioPressed){
		int P = radioPressed - 1;
				
		gl.glColor3f(1f, 0f, 0f);
		rectOnScreen(gl, x, y, groupHeight,groupWidth);
		
		drawText();
		
		for(int i = 0; i < amount; i++){
			
			this.list.get(i).setPressed(false);
	
			if(this.list.get(i).CursorInButton(PressedX,screenHeight- PressedY)){
				
				P = i;
			
			}
			this.list.get(i).draw();
			this.list.get(i).NegIfIn(CurrentX,screenHeight - CurrentY);
		}
		
		
		
	
		if(P >= 0){
			list.get(P).drawPressed();
			list.get(P).setPressed(true);
		}
		
		return P + 1;
	}
	
	public void addButton(int i, String string){
		this.list.add(new ToggleButton(this.gl, this.x - (groupWidth / 3f), (float)(this.y - (groupHeight*0.5*0.9 - buttonHeight/2.0f)), this.buttonWidth, this.buttonHeight, i, string));
		
//		if(amount == 0){
//			list.get(0).pressed = true;
//		}
		
		this.amount ++;
		
//		for(int i = 0; i < amount; i ++){
//			this.list.get(i).reduceWidth(amount);
//		}
	}
	
	public void drawText()
	{
		int x = Math.round(this.x + groupWidth*0.02f);
		int y = Math.round(this.y + groupHeight*0.35f);
		
		int length = string.length();
		
		GLUT glut = new GLUT();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glRasterPos2i(x - 17- 6*length/2, y - 7); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, string.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
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
