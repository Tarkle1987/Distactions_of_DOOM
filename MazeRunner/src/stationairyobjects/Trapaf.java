package stationairyobjects;


import java.util.ArrayList;
import javax.media.opengl.GL;
import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;
import movingobjects.VisibleObject;
/**
 * Class to load and display Stairs down
 * @author Tark
 *
 */

public class Trapaf implements VisibleObject{
	public boolean transport = false;
	private float size = 5;
	private double locationX,locationZ;
	public Maze maze;
	
	private double dLmax;
	public boolean inrange = false;
	/**
	 * Constructor that places a stairs down at x,z
	 * @param x x position to place stairs
	 * @param z z position to place stairs
	 */
	public Trapaf(float x, float z) {
		dLmax = (double) size;
		locationX = (double) x;
		locationZ = (double) z;
	}
	/**
	 * 
	 * @return returns x location of stairs down
	 */
	public double getLocationX(){
		return locationX;
	}
	/**
	 * 
	 * @return returns z location of stairs down
	 */
	public double getLocationZ(){
		return locationZ;
	}
	
	public void display(GL gl) {
	
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
		
		if(dLength < 1*dLmax&&dLength > 0.5*dLmax)
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
