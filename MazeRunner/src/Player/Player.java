package Player;
import movingobjects.GameObject;
import Maze.Maze;



/**
 * Player represents the actual player in MazeRunner.
 * <p>
 * This class extends GameObject to take advantage of the already implemented location 
 * functionality. Furthermore, it also contains the orientation of the Player, ie. 
 * where it is looking at and the player's speed. 
 * <p>
 * For the player to move, a reference to a Control object can be set, which can then
 * be polled directly for the most recent input. 
 * <p>
 * All these variables can be adjusted freely by MazeRunner. They could be accessed
 * by other classes if you pass a reference to them, but this should be done with 
 * caution.
 * 
 * @author Bruno Scheele
 *
 */
public class Player extends GameObject {	
	private double horAngle, verAngle;
	private double speed;
	private double sprintspeed = 0.015;
	private double godmodespeed = 0.02;

	public int hp;
	private int hittimer = 0;
	static final int hittimeInterval = 1;
	
	public boolean action = false;


	private Control control = null;

	private boolean GodMode = false;

	/**
	 * The Player constructor.
	 * <p>
	 * This is the constructor that should be used when creating a Player. It sets
	 * the starting location and orientation.
	 * <p>
	 * Note that the starting location should be somewhere within the maze of 
	 * MazeRunner, though this is not enforced by any means.
	 * 
	 * @param x		the x-coordinate of the location
	 * @param y		the y-coordinate of the location
	 * @param z		the z-coordinate of the location
	 * @param h		the horizontal angle of the orientation in degrees
	 * @param v		the vertical angle of the orientation in degrees
	 */
	public Player( double x, double y, double z, double h, double v ) {
		// Set the initial position and viewing direction of the player.
		super( x, y, z );
		horAngle = h;
		verAngle = v;
		speed = 0.01;
		hp = 6;
	}

	/**
	 * Sets the Control object that will control the player's motion
	 * <p>
	 * The control must be set if the object should be moved.
	 * @param input
	 */
	public void setControl(Control control)
	{
		this.control = control;
	}

	/**
	 * Gets the Control object currently controlling the player
	 * @return
	 */
	public Control getControl()
	{
		return control;
	}

	/**
	 * Returns the horizontal angle of the orientation.
	 * @return the horAngle
	 */
	public double getHorAngle() {
		return horAngle;
	}

	/**
	 * Sets the horizontal angle of the orientation.
	 * @param horAngle the horAngle to set
	 */
	public void setHorAngle(double horAngle) {
		this.horAngle = horAngle;
	}

	/**
	 * Returns the vertical angle of the orientation.
	 * @return the verAngle
	 */
	public double getVerAngle() {
		return verAngle;
	}

	/**
	 * Sets the vertical angle of the orientation.
	 * @param verAngle the verAngle to set
	 */
	public void setVerAngle(double verAngle) {
		this.verAngle = verAngle;
	}

	/**
	 * Returns the speed.
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Updates the physical location and orientation of the player
	 * @param deltaTime The time in milliseconds since the last update.
	 * @param maze 
	 * @return 
	 * @return 
	 */
	public void update(int deltaTime, Maze maze)
	{
		
		if (control != null)
		{
			control.update();


			
		if(control.y && control.s)
			GodMode = true;
			
			
			if(control.action){
				this.action = true;
			}else{
				this.action = false;
			}


			double Hor = getHorAngle() - (0.1*control.getdX());
			double Ver = getVerAngle() - (0.1*control.getdY());

			if( Ver > 89){
				Ver = 89;
			}
			if( Ver < - 89){
				Ver = - 89;
			}

			setHorAngle(Hor);
			setVerAngle(Ver);
			

			if (control.forward){
				// sprint (only forward
				if(control.sprint)
				{
					speed = sprintspeed;
				}
				
				if(GodMode)
					speed = godmodespeed;
				if (!maze.isWall(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle)) + 0.5 ,locationZ) && !maze.isWall(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle)) - 0.5 ,locationZ) || GodMode)
				{
					setLocationX(locationX - speed  * deltaTime * Math.sin(Math.toRadians(horAngle)));
				}
				if (!maze.isWall(locationX,locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle))+0.5)&& (!maze.isWall(locationX,locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle))-0.5))|| GodMode)
				{
					setLocationZ(locationZ - speed  * deltaTime * Math.cos(Math.toRadians(horAngle)));
				}


				// reset speed to default
				speed = 0.01;
			}
			if(GodMode)
				speed = godmodespeed;
			
			if (control.back){

				if(!maze.isWall(locationX + speed  * deltaTime * Math.sin(Math.toRadians(horAngle)) + 0.5, locationZ) && !maze.isWall(locationX + speed  * deltaTime * Math.sin(Math.toRadians(horAngle))-0.5, locationZ)|| GodMode)
				{
					setLocationX(locationX + speed  * deltaTime * Math.sin(Math.toRadians(horAngle)));
				}
				if(!maze.isWall(locationX, locationZ + speed  * deltaTime * Math.cos(Math.toRadians(horAngle)) + 0.5)&& !maze.isWall(locationX, locationZ + speed  * deltaTime * Math.cos(Math.toRadians(horAngle)) -0.5)|| GodMode)
				{
					setLocationZ(locationZ + speed  * deltaTime * Math.cos(Math.toRadians(horAngle)));
				}
			}
			if (control.left){
				if(!maze.isWall(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle + 90)) + 0.5, locationZ)&&!maze.isWall(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle + 90))-0.5,locationZ)|| GodMode)
				{
					setLocationX(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle + 90)));
				}
				if(!maze.isWall(locationX, (locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle + 90)))+0.5) && !maze.isWall(locationX, (locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle + 90)))-0.5)|| GodMode)
				{
					setLocationZ(locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle + 90)));
				}
			}
			if (control.right){
				if(!maze.isWall((locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle - 90)))+0.5, locationZ)&& !maze.isWall((locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle - 90))) -0.5, locationZ)|| GodMode)
				{
					setLocationX(locationX - speed * deltaTime * Math.sin(Math.toRadians(horAngle - 90)));
				}
				if(!maze.isWall(locationX, locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle - 90)) +0.5) && !maze.isWall(locationX, locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle - 90))-0.5)|| GodMode)
				{
					setLocationZ(locationZ - speed * deltaTime * Math.cos(Math.toRadians(horAngle - 90)));
				}
			}
			if(control.space){
				setVerAngle(0);
			}

			if(GodMode){
				if(control.up){
					setLocationY(locationY + speed * deltaTime);
				}
				if(control.down){
					setLocationY(locationY - speed * deltaTime);
				}
			}

			if(control.hpdown){

				hp = hp -1;

				control.hpdown = false;
			}
			if(control.hpup){

				hp = hp + 1;

				control.hpup = false;
			}
		}


		if(hp > 6){
			hp = 6;
		}else if(hp < 0){
			hp = 0;
		}

		hittimer = hittimer - deltaTime;

	}

	public void setGodMode(boolean G){
		GodMode = G;
	}
	public boolean getGodMode(){
		return GodMode;
	}

	public void Hit(){

		if(hittimer <= 0){
			if(hp > 0){
				hp = hp - 1;
				hittimer = hittimeInterval*1000;
			}
		}


	}
}
