import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

/*
 * Already implemented:
 * - Random moving, but with momentum: If possible go straight.
 * 
 * Done: Store position in ArrayList that contains positions of all objects
 * Done: Prevent collisions with other objects.
 * TODO: Move in the direction of the player if the player is visible.
 * TODO: When moving randomly, check for crosspoints(at this moment Beer walks only to walls, and could get stuck in dead ends.
 */
public class Beer extends GameObject implements VisibleObject{

	private double speed;
	private double heigth;
	private int Xdirection;
	private int Zdirection;
	private Tile nextTile;
	private int beerNumber;

	public Beer(Tile tile, double heigth, int beerNumber)
	{
		super(tile.getX(), 0, tile.getZ());
		this.speed = 0.5;
		this.heigth = heigth;
		this.beerNumber = beerNumber;
		this.Xdirection = 0;
		this.Zdirection = 0;
	}

	@Override
	public void display(GL gl) {
		GLUT glut = new GLUT();
		float CilinderColor[] = { 1f, 1.0f, 0.12f, 0f };
		gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, CilinderColor, 0);

		gl.glPushMatrix();
		gl.glTranslated(this.locationX,this.locationY,this.locationZ);
		gl.glRotatef(-90, 1, 0, 0);
		glut.glutSolidCylinder(heigth, heigth, 100, 200);
		gl.glRotatef(90, 1, 0, 0);
		gl.glPopMatrix();
	}

	public Tile BeerMove(Maze maze, ArrayList<Tile> objectPositions)
	{
		ArrayList<Tile> possibleStates = new ArrayList<Tile>(); // List of possibleStates, send to method move.
		Tile currentTile = new Tile(this.getLocationX(), this.getLocationZ());
		/*
		 * To implement momentum, we first check if the option in the direction of the previous direction isn't a wall.
		 * That is separately checked for the X and Z direction. This option is added to the list of states
		 */
		if(Xdirection != 0 && !maze.isWall(currentTile.getX() + Xdirection*(heigth+speed), currentTile.getZ()) &&
				!maze.isWall(currentTile.getX() + Xdirection*(heigth+speed), currentTile.getZ() + heigth) &&
				!maze.isWall(currentTile.getX() + Xdirection*(heigth+speed), currentTile.getZ() - heigth))
		{
			Tile state = new Tile(currentTile.getX() + Xdirection*speed, currentTile.getZ());
			boolean check = true;
			for(int j =0; j<objectPositions.size(); j++)
			{
				if(state.distance(objectPositions.get(j)) < 2*heigth && j != this.beerNumber)
				{
					check = false;
					break;
				}
			}
			if(!possibleStates.contains(state) && check )
			{
				possibleStates.add(state);
			}
		}
		else if(Zdirection != 0 && !maze.isWall(currentTile.getX(), currentTile.getZ() + Zdirection*(heigth+speed)) &&
				!maze.isWall(currentTile.getX() + heigth, currentTile.getZ() + Zdirection*(heigth+speed)) &&
				!maze.isWall(currentTile.getX() - heigth, currentTile.getZ() + Zdirection*(heigth+speed)))
		{
			Tile state = new Tile(currentTile.getX(), currentTile.getZ() + Zdirection*speed);
			boolean check = true;
			for(int j =0; j<objectPositions.size(); j++)
			{
				if(state.distance(objectPositions.get(j)) < 2*heigth && j != this.beerNumber)
				{
					check = false;
					break;
				}
			}
			if(!possibleStates.contains(state) && check )
			{
				possibleStates.add(state);
			}
		}
/*
 * If the direction-momentum side is a wall, the possibleState size = 0, check then for other possibilities.
 */
		if(possibleStates.size() < 1)
		{
			for(int k=-1; k<=1; k++)
			{
				/*
				 * Check for 2 * 3 directions if the next step is a wall. Also two sides are checked, so that it isn't possible
				 * to move through walls. If it is not a wall, the Tile is added to the possibleStates.
				 */
				if(!maze.isWall(this.getLocationX() + k*(heigth+speed), this.getLocationZ()) && 
						!maze.isWall(this.getLocationX() + k*(heigth+speed), this.getLocationZ() + heigth) &&
						!maze.isWall(this.getLocationX() + k*(heigth+speed), this.getLocationZ() - heigth))
				{

					Tile state = new Tile(this.getLocationX()+k*speed,this.getLocationZ());
					boolean check = true;
					for(int j =0; j<objectPositions.size(); j++)
					{
						if(state.distance(objectPositions.get(j)) < 2*heigth && j != this.beerNumber)
						{
							check = false;
							break;
						}
					}
					if(!possibleStates.contains(state) && check )
					{
						possibleStates.add(state);
					}
				}

				if(!maze.isWall(this.getLocationX(), this.getLocationZ() + k*(heigth+speed)) && 
						!maze.isWall(this.getLocationX() + heigth, this.getLocationZ() + k*(heigth+speed)) &&
						!maze.isWall(this.getLocationX() - heigth, this.getLocationZ() + k*(heigth+speed)))
				{
					Tile state = new Tile(this.getLocationX(),this.getLocationZ()+k*speed);
					boolean check = true;
					for(int j =0; j<objectPositions.size(); j++)
					{
						if(state.distance(objectPositions.get(j)) < 2*heigth&& j != this.beerNumber)
						{
							check = false;
							break;
						}
					}
					if(!possibleStates.contains(state) && check )
					{
						possibleStates.add(state);
					}
				}
			}
		}
		nextTile = move(possibleStates, currentTile); // Function call that moves the object, and returns the new position
		/*
		 * Get the direction:
		 */
		if(nextTile.getX()-currentTile.getX() > 0.0)
			Xdirection = 1;
		else if(nextTile.getX()-currentTile.getX() < 0.0)
			Xdirection = -1;
		if(nextTile.getZ()-currentTile.getZ() > 0.0)
			Zdirection = 1;
		else if(nextTile.getZ()-currentTile.getZ() < 0.0)
			Zdirection = -1;
		return nextTile;		
	}

	/*
	 * Method that moves the object with the direction randomly chosen if there are more than one possible states.
	 */
	public Tile move(ArrayList<Tile> possible, Tile currentTile)
	{
		double kansPerState = 1.0/(possible.size() + 1);
		Tile res = currentTile;
		double randomnr = Math.random();
		for(int j = 1 ; j <= possible.size() ; j++) {
			if(j*kansPerState > randomnr) 
			{
				this.setLocationX(possible.get(j-1).getX());
				this.setLocationZ(possible.get(j-1).getZ());
				res = possible.get(j-1);
				break;
			}
		}
		if(possible.size() == 1)
		{
			res = possible.get(0);
		}
		return res;
	}

	@Override
	public void update(int deltaTime, Maze maze, double playerX, double playerZ) {
		// TODO Auto-generated method stub

	}

}

