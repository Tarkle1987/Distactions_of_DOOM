import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class CompanionCube extends GameObject implements VisibleObject {

	public double size;
	private double speed;
	
	public CompanionCube(double x, double y, double z,  double size){
		
		super(x,y + size/2,z);
		this.size = size;
		speed = 0.01;
	}
	
	
	public void display(GL gl) {
		// TODO Auto-generated method stub
		GLUT glut = new GLUT();
		
		float CubeColor[] = { 1f, 0.627f, 0f, 1f };
		gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, CubeColor, 0);
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
	}
	
	public void CubeMove(double X, double Z){
		//CubeTouchDetection(X,Z);
		switch (CubeTouchDetection(X,Z)){
		case 1: while(X > this.locationX - size/2 -1){this.locationX = this.locationX + speed;}
			break;
		case 2: while(Z > this.locationZ - size/2 -1){this.locationZ = this.locationZ + speed;}
			break;
		case 3: while(X < this.locationX + size/2 + 1){this.locationX = this.locationX - speed;}
			break;
		case 4: while(Z < this.locationZ + size/2 + 1){this.locationZ = this.locationZ - speed;}
			break;
		}
		
	}
	
	public int CubeTouchDetection(double X, double Z){
		int quadrant = 0;
		
		X = X - this.locationX;
		Z = Z - this.locationZ;
		
			
		if(X > -size/2 -1 && X < 0.9*(-size/2)){
			if(Z > -size/2 && Z < size/2){
				quadrant = 1;
			}
		}else if(X < size/2 + 1 && X > 0.9*(size/2)){
			if(Z > -size/2 && Z < size/2){
				quadrant = 3;
			}
		}else if(Z > -size/2 - 1 && Z < 0.9*(-size/2)){
			if(X > -size/2 && X < size/2){
				quadrant = 2;
			}
		}else if(Z < size/2 + 1 && Z > 0.9*(size/2)){
			if(X > -size/2 && X < size/2){
				quadrant = 4;
			}
		}
		

		
		return quadrant;
	}


	@Override
	public void update(int deltaTime, Maze maze) {
		// TODO Auto-generated method stub
		
	}

}
