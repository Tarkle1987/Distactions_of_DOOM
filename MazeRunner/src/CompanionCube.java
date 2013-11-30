import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class CompanionCube extends GameObject implements VisibleObject {

	public double size;
	private double speed;

	private double angle;
	protected double newangle;
	private double anglespeed;

	private int sightrange = 0;

	private double directionX = 0;
	private double directionZ = 0;
	private double sightX = 0;
	private double sightZ = 0;
	private int signX = 1;
	private int signZ = 1;
	int momentum = 10 *16;

	public CompanionCube(double x, double y, double z,  double size){

		super(x,y + size/2,z);
		this.size = size;
		speed = 0.01;
		angle = 0;
		newangle = angle;
		anglespeed = 0.1;
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

		}else{
			momentum = momentum - deltaTime;

			if(momentum <= 0){
				momentum = 100*16;

				double random = Math.random()*2*Math.PI;

				dX = Math.cos(random);
				dZ = Math.sin(random);


			}else{
				dX = directionX;
				dZ = directionZ;
			}

			
			int[] Surround = CheckSurround(maze);
			
			if(Surround[0] == 1 && Surround[1] == 0 && Surround[2] == 1 && Surround[3] == 0){
				if(dZ < 0 && signZ > 0){
					dZ = dZ*-1;
				}else if(dZ > 0 && signZ < 0){
					dZ = dZ * -1;
				}
			}
			if(Surround[0] == 0 && Surround[1] == 1 && Surround[2] == 0 && Surround[3] == 1){
				if(dX < 0 && signX > 0){
					dX = dX*-1;
				}else if(dX > 0 && signX < 0){
					dX = dX * -1;
				}
			}
		}

		sightX = dX;
		sightZ = dZ;

		signX =1;
		signZ = 1;

		if(dX < 0 ){
			signX = -1;
		}
		if(dZ < 0){
			signZ = -1;
		}


		locationX = locationX + dX * speed*deltaTime;
		locationZ = locationZ + dZ * speed*deltaTime;


		if(maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX,locationZ-(size*Math.sqrt(2))/2)||
				maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX+(size*Math.sqrt(2))/2, locationZ-(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ-(size*Math.sqrt(2))/2)){
			locationX = locationX - dX * speed*deltaTime;
			locationZ = locationZ - dZ * speed*deltaTime + signZ*(dZ/dZ)*speed*deltaTime;

			sightX = 0;
			sightZ = signZ*(dZ/dZ);


			if(maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX,locationZ-(size*Math.sqrt(2))/2)||
					maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX+(size*Math.sqrt(2))/2, locationZ-(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ-(size*Math.sqrt(2))/2)){
				locationX = locationX + signX*(dX/dX)*speed*deltaTime;
				locationZ = locationZ - signZ*(dZ/dZ)*speed*deltaTime;

				sightX = signX*(dX/dX);
				sightZ = 0;


				if(maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX,locationZ-(size*Math.sqrt(2))/2)||
						maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX+(size*Math.sqrt(2))/2, locationZ-(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ-(size*Math.sqrt(2))/2)){
					locationX = locationX -  signX*(dX/dX)*speed*deltaTime;

					sightX = dX;
					sightZ = dZ;

					momentum = 0;
				}
			}
		}

		directionX = dX;
		directionZ = dZ;

	}

	private void CubeRotate(int deltaTime){
		// trying to let the cube turn to the player

		// Vector van de speler bepalen ten opzichte van de kubus
		double dx = this.sightX;
		double dz = this.sightZ;

		// Lengte van de vector bepalen
		double dlength = Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2));


		// activeerd wanneer de speler zich binnen de zichtradius bevindt


		// Hoek berekenen tussen de kubus en de speler
		dx = dx/dlength;
		dz = dz/dlength;

		newangle = -Math.toDegrees(Math.atan2(dz, dx));	


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



		// Bewegen van kubus naar player / door player
		CubeMove(deltaTime, maze, X,Z);

		// kubus roteerd naar de player
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);

	}
	
	public int[] CheckSurround(Maze maze){
		int[] Surround = new int[4];
		
		int i = maze.convertToGridX(this.locationX);
		int j = maze.convertToGridZ(this.locationZ);
		
		Surround[0] = maze.maze[i-1][j];	// Left (X-1)
		Surround[1] = maze.maze[i][j - 1];	// Down (Y-1)
		Surround[2] = maze.maze[i+1][j];	// Right (X+1)
		Surround[3] = maze.maze[i][j+1];	// Up   (Y+1)
				
		return Surround;
	}



	@Override
	public Tile getPosition() 
	{
		return new Tile(this.getLocationX(), this.getLocationZ());
	}


	@Override
	public boolean getDestroy() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setDestroy(boolean set) {
		// TODO Auto-generated method stub

	}

}
