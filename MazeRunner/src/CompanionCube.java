import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class CompanionCube extends GameObject implements VisibleObject {

	public double size;
	private double speed;
	
	private double angle;
	protected double newangle;
	private double anglespeed;
	
	private int sightrange = 15;
	
	public CompanionCube(double x, double y, double z,  double size){
		
		super(x,y + size/2,z);
		this.size = size;
		speed = 0.01;
		angle = 0;
		newangle = angle;
		anglespeed = 0.4;
	}
	
	
	public void display(GL gl) {
		// TODO Auto-generated method stub
		GLUT glut = new GLUT();
		
		float CubeColor[] = { 1f, 0.627f, 0f, 1f };
		gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, CubeColor, 0);
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		
		gl.glRotated(angle, 0,1,0);
		
		
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
	}
	
	public void CubeMove(int deltaTime, Maze maze, double X, double Z){

		// De kubus bewegen als de speler ertegenaan loopt ( alleen in X of in Z richting )
//		switch (CubeTouchDetection(X,Z)){
//		case 1: while(X > this.locationX - size/2 -1){this.locationX = this.locationX + speed*deltaTime;}
//			break;
//		case 2: while(Z > this.locationZ - size/2 -1){this.locationZ = this.locationZ + speed*deltaTime;}
//			break;
//		case 3: while(X < this.locationX + size/2 + 1){this.locationX = this.locationX - speed*deltaTime;}
//			break;
//		case 4: while(Z < this.locationZ + size/2 + 1){this.locationZ = this.locationZ - speed*deltaTime;}
//			break;
//		}
		
		
		
		// kubus loopt richting player
		double dX = X - locationX;
		double dZ = Z - locationZ;
		
		double dLength = Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
		
		if(dLength < sightrange && dLength > 2){
		
			dX = dX/dLength;
			dZ = dZ/dLength;
		
			locationX = locationX + dX * speed*deltaTime;
			locationZ = locationZ + dZ * speed*deltaTime;
		
			
			if(maze.isWall(locationX, locationZ)){
				locationX = locationX - dX * speed*deltaTime;
				
				if(maze.isWall(locationX, locationZ)){
					locationX = locationX + dX * speed*deltaTime;
					locationZ = locationZ - dZ * speed*deltaTime;
					
					if(maze.isWall(locationX, locationZ)){
						locationX = locationX - dX * speed*deltaTime;
					}
				}
			}
		}
	}
		
	private void CubeRotate(int deltaTime, double X, double Z){
		// trying to let the cube turn to the player
		
		// Vector van de speler bepalen ten opzichte van de kubus
		double dx = X - this.locationX;
		double dz = Z - this.locationZ;
		
		// Lengte van de vector bepalen
		double dlength = Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2));
		
		
		// activeerd wanneer de speler zich binnen de zichtradius bevindt
		if(dlength < sightrange){ 
		
			// Hoek berekenen tussen de kubus en de speler
			dx = dx/dlength;
			dz = dz/dlength;
	
			newangle = -Math.toDegrees(Math.atan2(dz, dx));	
		}
		
		// De kubus een nieuwe hoek meegeven zodat het verschil met de hoek van de speler kleiner wordt
		if(angle < newangle){
			
			if((angle - newangle) < -180){
				angle = angle - anglespeed*deltaTime;
			}else{
				angle = angle + anglespeed*deltaTime;
			}
			
			if(angle > 180)
				angle = angle - 360;
			if(angle < -180)
				angle = angle + 360;
		}
		if(angle > newangle){
		
			
			if((angle - newangle) > 180){
				angle = angle + anglespeed*deltaTime;
			}else{
				angle = angle - anglespeed*deltaTime;
			}
			
			if(angle > 180)
				angle = angle - 360;
			if(angle < -180)
				angle = angle + 360;
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
	public void update(int deltaTime, Maze maze, ArrayList<VisibleObject> visibleObjects ,Player player) {
		double X = player.locationX;
		double Z = player.locationZ;
		
		// kubus roteerd naar de player
		CubeRotate(deltaTime, X,Z);
		
		// Bewegen van kubus naar player / door player
		CubeMove(deltaTime, maze, X,Z);
		
	}


	@Override
	public Tile getPosition() 
	{
		return new Tile(this.getLocationX(), this.getLocationZ());
	}

}
