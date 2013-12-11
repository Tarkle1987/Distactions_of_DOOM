
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


public class CompanionCube extends GameObject implements Lifeform {

	// Cube properties
	public double size;
	private double speed;
	private double angle;
	protected double newangle;
	private double anglespeed;


	// Follow player attributes
	private double[] PlayerLocation = new double[2];
	
	private int follow = 0;
	static final int followtime = 15;
	private int routeplanner = 0;
	static final int routeplannertime = 30;
	
	private int sightrange = 60;
	private int hearingrange = 10;
	private boolean sight = false;
	
	private boolean playerhit = false;
	
	// Movement Variables
	private boolean stun = false;
	private int stuntimer = 0;
	static final int stuntime = 2;
	private boolean freemovement = false;
	private double directionX = 0;
	private double directionZ = 1;
	private double sightX = 0;
	private double sightZ = 1;
	private int signX = 1;
	private int signZ = 1;
	int momentum = 0;

	public CompanionCube(double x, double y, double z,  double size){

		super(x,y + size/2,z);
		this.size = size;
		speed = 0.005;
		angle = 0;
		newangle = angle;
		anglespeed = 0.1;
		
	}


	public void display(GL gl) {
		GLUT glut = new GLUT();

		
		float[] CubeColor = { 0.1333f, 0.545f, 0.1333f, 1f };
		
		if(routeplanner > 0)
			CubeColor = new float[]{ 0f, 0f, 0f, 1f};
		if(follow > 0)
			CubeColor = new float[]{ 1f, 0.627f, 0f, 1f };
		if(sight)
			CubeColor = new float[]{ 1f, 0f, 0f, 1f };
		if(stun)
			CubeColor = new float[]{ 0f, 0f, 1f, 1f };
		
		
		gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, CubeColor, 0);

		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(angle, 0,1,0);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY + size,locationZ);
		gl.glRotated(angle, 0,1,0);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		
	
	}

	public void CubeMove(int deltaTime, Maze maze, double X, double Z){

		System.out.println(routeplanner);
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

		sight = CheckSight(X,Z, maze);
		
		if(sight){
			momentum = 0;

			follow = followtime*1000;
			
			PlayerLocation[0] = X;
			PlayerLocation[1] = Z;
	
			if(maze.isWall(PlayerLocation[0], PlayerLocation[1])){
				follow = 0;
			}

			
			if(dLength > 1.5*size){
				dX = dX/dLength;
				dZ = dZ/dLength;}
			else{
				dX = 0;
				dZ = 0;
				playerhit = true;
			}


		}else if(follow > 0){
			momentum = 0;
			routeplanner = 0;
			
			dX = PlayerLocation[0] - locationX;
			dZ = PlayerLocation[1] - locationZ;

	


			dLength = Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));

			if(dLength > size){
				dX = dX / dLength;
				dZ = dZ / dLength;
			}else{
				dX = 0;
				dZ = 0;

				follow = 0;
			}
		}else if(routeplanner > 0) {
			Routeplanner nieuw = new Routeplanner();
			int direction = nieuw.getRoute(maze, new Tile(this.locationX, this.locationZ), new Tile(X,Z));
			System.out.println("direction: " + direction);
			
			if(direction == 1){
				dX = -1;
				dZ = 0;
		
				momentum = 0;
			}else if(direction == 2){
				dX = 0;
				dZ = 1;

				momentum = 0;
			}else if(direction == 3){
				dX = 1;
				dZ = 0;
		
				momentum = 0;
			}else if(direction == 4){
				dX = 0;
				dZ = -1;
	
				momentum = 0;
			}else {
			    freemovement = true;
				dX = 0;
				dZ = 0;
			}
		}else {
			freemovement = true;
		}
		
		if(freemovement) {
			freemovement = false;
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


			double[] dW = CheckSurround(maze, dX, dZ);

			dX = dW[0];
			dZ = dW[1];

		}

		sightX = dX;
		sightZ = dZ;

		signX = 1;
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
			locationZ = locationZ - dZ * speed*deltaTime + signZ*speed*deltaTime;

			sightX = 0;
			sightZ = signZ;


			if(maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX,locationZ-(size*Math.sqrt(2))/2)||
					maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX+(size*Math.sqrt(2))/2, locationZ-(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ-(size*Math.sqrt(2))/2)){
				locationX = locationX + signX*speed*deltaTime;
				locationZ = locationZ - signZ*speed*deltaTime;

				sightX = signX;
				sightZ = 0;


				if(maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ)||maze.isWall(locationX,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX,locationZ-(size*Math.sqrt(2))/2)||
						maze.isWall(locationX+(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX+(size*Math.sqrt(2))/2, locationZ-(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ+(size*Math.sqrt(2))/2)||maze.isWall(locationX-(size*Math.sqrt(2))/2,locationZ-(size*Math.sqrt(2))/2)){
					locationX = locationX -  signX*speed*deltaTime;

					sightX = dX;
					sightZ = dZ;

					momentum = 0;
				}
			}
		}
		directionX = dX;
		directionZ = dZ;
		
		if(follow > 0)
			follow = follow - deltaTime;
		if(routeplanner > 0)
			routeplanner = routeplanner - deltaTime;
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
	public void update(int deltaTime, Maze maze, Player player) {
		playerhit = false;
		
		double X = player.locationX;
		double Z = player.locationZ;

		stuntimer = stuntimer - deltaTime;

		if(stuntimer <= 0){
			stun = false;
		}


		if(!stun){

		// Bewegen van kubus naar player / door player
		CubeMove(deltaTime, maze, X,Z);

		// kubus roteerd naar de player
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		}

	}

	public double[] CheckSurround(Maze maze, double X, double Z){
		int[] Surround = new int[4];
		double dX = X;
		double dZ = Z;

		int i = maze.convertToGridX(this.locationX);
		int j = maze.convertToGridZ(this.locationZ);

		if(i > 0 && i < 44 && j > 0 && j < 44){
			Surround[0] = maze.maze[i-1][j];	// Left (X-1)
			Surround[1] = maze.maze[i][j - 1];	// Down (Y-1)
			Surround[2] = maze.maze[i+1][j];	// Right (X+1)
			Surround[3] = maze.maze[i][j+1];	// Up   (Y+1)
		}

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

		double[] dW = {dX , dZ };

		return dW;

	}

	private boolean CheckSight(double X, double Z, Maze maze){
		boolean sight = false;

		double dX = X - locationX;
		double dZ = Z - locationZ;

		double dLength = Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));

		dX  = dX / dLength;
		dZ = dZ / dLength;

		
		double hoek = Math.toDegrees(Math.asin(dX*sightX + dZ*sightZ));		
		
		if((dLength < sightrange && hoek > 10) || dLength < hearingrange){
			sight = true;

			double x = locationX;
			double z = locationZ;

			double dx = X - x;
			double dz = Z - z;

			double dL = Math.sqrt(Math.pow(dx,2)+Math.pow(dz,2));


			while(dL > size){
				x = x + dX;
				z = z + dZ;

				if(maze.isWall(x, z)){
					sight = false;
				}

				dx = X - x;
				dz = Z - z;

				dL = Math.sqrt(Math.pow(dx,2)+Math.pow(dz,2));
			}

		}

		return sight;
	}


	@Override
	public boolean isHit(Projectile p) {
		boolean hit = false;

		double x = p.locationX;
		double y = p.locationY;
		double z = p.locationZ;

		if((x > locationX - 0.75*size) && (x < locationX + 0.75*size) 
				&& (y > locationY - 0.5*size) && (y < locationY + 1.75*size) 
				&& (z > locationZ - 0.75*size) && (z < locationZ + 0.75*size)){
			hit = true;
		}

		if(hit){
			stun = true;
			stuntimer = stuntime*1000;
		}

		return hit;
	}
	
	@Override
	public double getSize(){
		return size;
	}
	
	public boolean isCube(CompanionCube c){
		boolean iscube = false;
		
		double dX = c.locationX - locationX;
		double dZ = c.locationZ - locationZ;
		
		double dLength =  Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
		
		if(dLength < size){
			iscube = true;
		}
		
		return iscube;
	}


	@Override
	public boolean Collide(Lifeform l) {
		boolean iscube = false;
		
		double dX = l.getLocationX() - locationX;
		double dZ = l.getLocationZ() - locationZ;
		
		double dLength =  Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
		
		if(dLength < (size/2 + l.getSize()/2) ){
			iscube = true;
		}
		
		return iscube;
	}


	@Override
	public void stepBack(int deltaTime) {
		locationX = locationX + sightX*speed*-deltaTime;
		locationZ = locationZ + sightZ*speed*-deltaTime;
		
		momentum = 0; 
	}
	
	@Override
	public boolean getPlayerHit(){
		return playerhit;
	}


	@Override
	public double[] getPlayerLocation() {
	
		return PlayerLocation;
	}


	@Override
	public void SetPlayerLocation(double[] PL) {
		
		this.PlayerLocation = PL;
		
		routeplanner = routeplannertime*1000;
	}
	
	@Override
	public boolean getSight(){
		return sight;
	}
	
	
	
	

}
