import javax.media.opengl.GL;


public class Heart {
	
	private	float x;
	private float y;
	private int size = 3;
	private byte status = Full;
	protected boolean Groot = false;
	private int sizegroot = 9;
	
	private static final byte Full = 2;
	private static final byte Half = 1;
	private static final byte Empty = 0;
	
	public Heart(float x, float y){
		this.x = x;
		this.y = y;
		
	}
	
	private void pointOnScreen(GL gl, float x, float y) {
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(x, y);
		gl.glEnd();
	}
	private void pointOnScreen2(GL gl, float x, float y){
		pointOnScreen(gl, x, y);
		pointOnScreen(gl, x, y + 1);
		pointOnScreen(gl, x, y - 1);
		pointOnScreen(gl, x+1, y );
		pointOnScreen(gl, x+1, y + 1);
		pointOnScreen(gl, x+1, y - 1);
		pointOnScreen(gl, x-1, y);
		pointOnScreen(gl, x-1, y + 1);
		pointOnScreen(gl, x-1, y - 1);
	}
	private void pointOnScreen3(GL gl, float x, float y){
		pointOnScreen2(gl, x, y);
		pointOnScreen2(gl, x, y + 3);
		pointOnScreen2(gl, x, y - 3);
		pointOnScreen2(gl, x+3, y );
		pointOnScreen2(gl, x+3, y + 3);
		pointOnScreen2(gl, x+3, y - 3);
		pointOnScreen2(gl, x-3, y);
		pointOnScreen2(gl, x-3, y + 3);
		pointOnScreen2(gl, x-3, y - 3);
	}
	
	public void display(GL gl){
		
		
		if(!this.Groot){
			drawOutlining(gl);
		}else if(this.Groot){
			drawOutlining2(gl);
		}
	
	}
	
	

	public void drawOutlining(GL gl){
		int s = size;
		
		gl.glColor3f(0, 0, 0);
		
		pointOnScreen2(gl, x, y);
		pointOnScreen2(gl, x, y + 1*s);
		pointOnScreen2(gl, x, y + 2*s);
		pointOnScreen2(gl, x + 1*s, y + 3*s);
		pointOnScreen2(gl, x + 2*s, y + 4*s);
		pointOnScreen2(gl, x + 3*s, y + 4*s);
		pointOnScreen2(gl, x + 4*s, y + 3*s);
		pointOnScreen2(gl, x + 5*s, y + 3*s);
		pointOnScreen2(gl, x + 6*s, y + 4*s);
		pointOnScreen2(gl, x + 7*s, y + 4*s);
		pointOnScreen2(gl, x + 8*s, y + 3*s);
		pointOnScreen2(gl, x + 9*s, y + 2*s);
		pointOnScreen2(gl, x + 9*s, y + 1*s);
		pointOnScreen2(gl, x + 9*s, y);
		pointOnScreen2(gl, x + 8*s, y - 1*s);
		pointOnScreen2(gl, x + 7*s, y - 2*s);
		pointOnScreen2(gl, x + 6*s, y - 3*s);
		pointOnScreen2(gl, x + 5*s, y - 4*s);
		pointOnScreen2(gl, x + 4*s, y - 4*s);
		pointOnScreen2(gl, x + 3*s, y - 3*s);
		pointOnScreen2(gl, x + 2*s, y - 2*s);
		pointOnScreen2(gl, x + 1*s, y - 1*s);
		
		if(status == Half || status == Full){
			gl.glColor3f(1f,0f,0f);
			
			pointOnScreen2(gl, x + 1*s, y);
			pointOnScreen2(gl, x + 1*s, y + 1*s);
			pointOnScreen2(gl, x + 1*s, y + 2*s);
			
			pointOnScreen2(gl, x + 2*s, y - 1*s);
			pointOnScreen2(gl, x + 2*s, y + 0*s);
			pointOnScreen2(gl, x + 2*s, y + 2*s);
			pointOnScreen2(gl, x + 2*s, y + 3*s);
			
			pointOnScreen2(gl, x + 3*s, y - 2*s);
			pointOnScreen2(gl, x + 3*s, y - 1*s);
			pointOnScreen2(gl, x + 3*s, y + 0*s);
			pointOnScreen2(gl, x + 3*s, y + 1*s);
			pointOnScreen2(gl, x + 3*s, y + 3*s);
			
			pointOnScreen2(gl, x + 4*s, y - 3*s);
			pointOnScreen2(gl, x + 4*s, y - 2*s);
			pointOnScreen2(gl, x + 4*s, y - 1*s);
			pointOnScreen2(gl, x + 4*s, y - 0*s);
			pointOnScreen2(gl, x + 4*s, y + 1*s);
			pointOnScreen2(gl, x + 4*s, y + 2*s);
			
			gl.glColor3f(1f,1f,1f);
			pointOnScreen2(gl, x + 2*s, y + 1*s);
			pointOnScreen2(gl, x + 3*s, y + 2*s);
		}
		if(status == Full){
			gl.glColor3f(1f,0f,0f);
			
			pointOnScreen2(gl, x + 8*s, y);
			pointOnScreen2(gl, x + 8*s, y + 1*s);
			pointOnScreen2(gl, x + 8*s, y + 2*s);
			
			pointOnScreen2(gl, x + 7*s, y + 1*s);
			pointOnScreen2(gl, x + 7*s, y - 1*s);
			pointOnScreen2(gl, x + 7*s, y + 0*s);
			pointOnScreen2(gl, x + 7*s, y + 2*s);
			pointOnScreen2(gl, x + 7*s, y + 3*s);
			
			pointOnScreen2(gl, x + 6*s, y + 2*s);
			pointOnScreen2(gl, x + 6*s, y - 2*s);
			pointOnScreen2(gl, x + 6*s, y - 1*s);
			pointOnScreen2(gl, x + 6*s, y + 0*s);
			pointOnScreen2(gl, x + 6*s, y + 1*s);
			pointOnScreen2(gl, x + 6*s, y + 3*s);
			
			pointOnScreen2(gl, x + 5*s, y - 3*s);
			pointOnScreen2(gl, x + 5*s, y - 2*s);
			pointOnScreen2(gl, x + 5*s, y - 1*s);
			pointOnScreen2(gl, x + 5*s, y - 0*s);
			pointOnScreen2(gl, x + 5*s, y + 1*s);
			pointOnScreen2(gl, x + 5*s, y + 2*s);
			
			
		}
	
	}
	
	private void drawOutlining2(GL gl) {
		int s = sizegroot;
			
			gl.glColor3f(0, 0, 0);
			
			pointOnScreen3(gl, x, y);
			pointOnScreen3(gl, x, y + 1*s);
			pointOnScreen3(gl, x, y + 2*s);
			pointOnScreen3(gl, x + 1*s, y + 3*s);
			pointOnScreen3(gl, x + 2*s, y + 4*s);
			pointOnScreen3(gl, x + 3*s, y + 4*s);
			pointOnScreen3(gl, x + 4*s, y + 3*s);
			pointOnScreen3(gl, x + 5*s, y + 3*s);
			pointOnScreen3(gl, x + 6*s, y + 4*s);
			pointOnScreen3(gl, x + 7*s, y + 4*s);
			pointOnScreen3(gl, x + 8*s, y + 3*s);
			pointOnScreen3(gl, x + 9*s, y + 2*s);
			pointOnScreen3(gl, x + 9*s, y + 1*s);
			pointOnScreen3(gl, x + 9*s, y);
			pointOnScreen3(gl, x + 8*s, y - 1*s);
			pointOnScreen3(gl, x + 7*s, y - 2*s);
			pointOnScreen3(gl, x + 6*s, y - 3*s);
			pointOnScreen3(gl, x + 5*s, y - 4*s);
			pointOnScreen3(gl, x + 4*s, y - 4*s);
			pointOnScreen3(gl, x + 3*s, y - 3*s);
			pointOnScreen3(gl, x + 2*s, y - 2*s);
			pointOnScreen3(gl, x + 1*s, y - 1*s);
			
			if(status == Half || status == Full){
				gl.glColor3f(1f,0f,0f);
				
				pointOnScreen3(gl, x + 1*s, y);
				pointOnScreen3(gl, x + 1*s, y + 1*s);
				pointOnScreen3(gl, x + 1*s, y + 2*s);
				
				pointOnScreen3(gl, x + 2*s, y - 1*s);
				pointOnScreen3(gl, x + 2*s, y + 0*s);
				pointOnScreen3(gl, x + 2*s, y + 2*s);
				pointOnScreen3(gl, x + 2*s, y + 3*s);
				
				pointOnScreen3(gl, x + 3*s, y - 2*s);
				pointOnScreen3(gl, x + 3*s, y - 1*s);
				pointOnScreen3(gl, x + 3*s, y + 0*s);
				pointOnScreen3(gl, x + 3*s, y + 1*s);
				pointOnScreen3(gl, x + 3*s, y + 3*s);
				
				pointOnScreen3(gl, x + 4*s, y - 3*s);
				pointOnScreen3(gl, x + 4*s, y - 2*s);
				pointOnScreen3(gl, x + 4*s, y - 1*s);
				pointOnScreen3(gl, x + 4*s, y - 0*s);
				pointOnScreen3(gl, x + 4*s, y + 1*s);
				pointOnScreen3(gl, x + 4*s, y + 2*s);
				
				gl.glColor3f(1f,1f,1f);
				pointOnScreen3(gl, x + 2*s, y + 1*s);
				pointOnScreen3(gl, x + 3*s, y + 2*s);
			}
			if(status == Full){
				gl.glColor3f(1f,0f,0f);
				
				pointOnScreen3(gl, x + 8*s, y);
				pointOnScreen3(gl, x + 8*s, y + 1*s);
				pointOnScreen3(gl, x + 8*s, y + 2*s);
				
				pointOnScreen3(gl, x + 7*s, y + 1*s);
				pointOnScreen3(gl, x + 7*s, y - 1*s);
				pointOnScreen3(gl, x + 7*s, y + 0*s);
				pointOnScreen3(gl, x + 7*s, y + 2*s);
				pointOnScreen3(gl, x + 7*s, y + 3*s);
				
				pointOnScreen3(gl, x + 6*s, y + 2*s);
				pointOnScreen3(gl, x + 6*s, y - 2*s);
				pointOnScreen3(gl, x + 6*s, y - 1*s);
				pointOnScreen3(gl, x + 6*s, y + 0*s);
				pointOnScreen3(gl, x + 6*s, y + 1*s);
				pointOnScreen3(gl, x + 6*s, y + 3*s);
				
				pointOnScreen3(gl, x + 5*s, y - 3*s);
				pointOnScreen3(gl, x + 5*s, y - 2*s);
				pointOnScreen3(gl, x + 5*s, y - 1*s);
				pointOnScreen3(gl, x + 5*s, y - 0*s);
				pointOnScreen3(gl, x + 5*s, y + 1*s);
				pointOnScreen3(gl, x + 5*s, y + 2*s);
				
				
			}
		}
	public void setStatus(int Status){
		switch (Status){
		case 0:
			this.status = Empty;
			break;
		case 1: 
			this.status = Half;
			break;
		case 2: 
			this.status = Full;
			break;
		}
	}
	public int getSize(){
		if(Groot){
			return sizegroot;
		}else{
			return size;
		}
	}
	
	
	
}
