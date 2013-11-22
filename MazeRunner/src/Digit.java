import javax.media.opengl.GL;


public class Digit {

	private int digit;

	protected float size;
	private float delta = 4/10f;
	
	public Digit(){
		digit = 0;
		this.size = 1f;
		
	}
	
	public void set(int Digit){
		this.digit = Digit;
	}
	
	public void display(GL gl, float x, float y){
		gl.glColor3f(1f, 1f, 0f);
		
		// bovenste
		if(digit == 0 || digit == 2 || digit == 3 || digit == 5 || digit == 6 || digit == 7 || digit == 8 || digit == 9)
			barHor(gl, x, y);
		
		// boven links
		if(digit == 0 || digit == 4 || digit == 5 || digit == 6 || digit == 8 || digit == 9)
			barVer(gl, x -12*size - delta*size, y - 12*size -delta*size);
		
		// boven rechts
		if(digit == 0 || digit == 1 || digit == 2 || digit == 3 || digit == 4 || digit == 6 || digit == 7 || digit == 8 || digit == 9)
			barVer(gl, x + 12*size +delta*size, y - 12*size -delta*size);
		
		// middelste
		if(digit == 2 || digit == 3 || digit == 4 || digit == 5 || digit == 6 || digit == 8 || digit == 9)
			barHor(gl, x, y - 2*(12+delta)*size );
		
		// onder links
		if(digit == 0 || digit == 2 || digit == 6 || digit == 8)
			barVer(gl, x -12*size - delta*size, y - 3*(12+delta)*size);
		
		// onder rechts
		if(digit == 0 || digit == 1 || digit == 3 || digit == 4 || digit == 5 || digit == 6 || digit == 7 || digit == 8 || digit == 9)
			barVer(gl, x + 12*size +delta*size, y - 3*(12+delta)*size);
		
		// onderste
		if(digit == 0 || digit == 2 || digit == 3 || digit == 5 || digit == 6 || digit == 8 || digit == 9)
			barHor(gl, x, y - 4*(12+delta)*size );

	}
	
	private void barVer(GL gl, float x, float y){
		gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
		
		rectOnScreen(gl, x, y, 20*size, 4*size);
		triOnScreen(gl, x, y + 10*size, 2*size, 4*size, false);
		triOnScreen(gl, x, y - 10*size, 2*size, 4*size, true);
	}
	private void barHor(GL gl, float x, float y){
		gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
		
		rectOnScreen(gl, x, y, 4*size, 20*size);
		gl.glBegin(GL.GL_TRIANGLES);
		gl.glVertex2f(x + 10*size,y - 2*size);
		gl.glVertex2f(x + 12*size,y);
		gl.glVertex2f(x + 10*size,y + 2*size);
		
		gl.glEnd();
		gl.glBegin(GL.GL_TRIANGLES);
	
		gl.glVertex2f(x - 12*size,y);
		gl.glVertex2f(x - 10*size,y - 2*size);
		gl.glVertex2f(x - 10*size,y + 2*size);
		
		gl.glEnd();
		
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
	private void triOnScreen(GL gl, float x, float y, float height, float width, boolean reverse){
	
		
		gl.glBegin(GL.GL_TRIANGLES);
		
		if(reverse)
			gl.glVertex2f(x, y - height);
		gl.glVertex2f(x + width/2, y);
		if(!reverse)
			gl.glVertex2f(x, y + height);		
		gl.glVertex2f(x - width/2, y);
	
		
	
		gl.glEnd();
	}
}
