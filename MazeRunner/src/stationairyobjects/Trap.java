package stationairyobjects;

import java.util.ArrayList;
import javax.media.opengl.GL;
import movingobjects.CustomMazeObject;
import movingobjects.MazeObject;
import movingobjects.VisibleObject;
import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;
/**
 * Class to load and display Stairs up
 * @author Tark
 *
 */

public class Trap implements VisibleObject{
	public MazeObject Kaft1,Kaft2,Kaft3,Papier;
	public boolean transport = false;
	private float size = 5;
	private double locationX,locationZ;
	
	public Maze maze;
	
	private double dLmax;
	public boolean inrange = false;
	/**
	 * Constructor that places a stairs at x,z
	 * @param x x position to place stairs
	 * @param z z position to place stairs
	 */
	public Trap(float x, float z) {
		
		locationX = (double) x;
		locationZ = (double) z;
		dLmax = (double) size;
		Kaft1 = CustomMazeObject.readFromOBJ("Kaft1.obj",(float) 0.0175);
		Kaft1.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft1.rotateVerticesZ(-90, 1, 1);
		Kaft1.addColour("oranje");
		
		Kaft2 = CustomMazeObject.readFromOBJ("Kaft2.obj",(float) 0.0175);
		Kaft2.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft2.rotateVerticesZ(-90, 1, 1);
		Kaft2.addColour("oranje");
		
		Kaft3 = CustomMazeObject.readFromOBJ("Kaft3.obj",(float) 0.0175);
		Kaft3.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft3.rotateVerticesZ(-90, 1, 1);
		Kaft3.addColour("oranje");
		
		Papier = CustomMazeObject.readFromOBJ("Papier.obj",(float) 0.0175);
		Papier.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Papier.rotateVerticesZ(-90, 1, 1);
		Papier.addColour("wit");
		

	
	}
	/**
	 * 
	 * @return returns x location of stairs
	 */
	public double getLocationX(){
		return locationX;
	}
	/**
	 * 
	 * @return returns z location of stairs
	 */
	public double getLocationZ(){
		return locationZ;
	}
	/**
	 * Displays objects creating stairs
	 */
	@Override
	public void display(GL gl) {
		// TODO Auto-generated method stu
		Kaft1.display(gl);
		Kaft2.display(gl);
		Kaft3.display(gl);
		Papier.display(gl);
	}

	@Override
	public Tile getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Update function which determines if a player is transported or not 
	 */
	@Override
	public void update(int deltaTime, Maze maze,
			ArrayList<VisibleObject> visibleObjects, Player player) {

		this.maze = maze;
		double dX = player.locationX - maze.convertFromGridX((int)locationX)-2.5;
		double dZ = player.locationZ - maze.convertFromGridZ((int)locationZ)-2.5;
		
		double dLength =  Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
		if(dLength < 0.8*dLmax)
			inrange = true;
		else
			inrange = false;
			
		
		if(inrange && player.action){
			transport=true;
		}
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
