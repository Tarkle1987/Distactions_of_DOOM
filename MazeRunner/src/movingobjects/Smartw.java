package movingobjects;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;

import com.sun.opengl.util.texture.Texture;


public class Smartw implements VisibleObject{

	private MazeObject Smartw;
	private float size = 5;
	private double locationX,locationZ;
	protected boolean destroy = false;
	protected Texture texture;
	
	public Smartw(float x, float z) {
		locationX = (double) x;
		locationZ = (double) z;
		Smartw = CustomMazeObject.readFromOBJ("Smartwit.obj", 2);
		Smartw.setCor((float)(x+0.5)*size, (float)(z+0.5)*size,(float) 0.2*size);
//		Smartw.addColour("oranje");
	}
	
	public MazeObject getSmartw(){
		return Smartw;
	}
	
	public double getLocationX(){
		return locationX;
	}
	
	public double getLocationZ(){
		return locationZ;
	}
	
	public void addTexture(Texture t)
	{
		texture = t;
		Smartw.addTexture(texture);
	}
	
	@Override
	public void display(GL gl) {
		Smartw.display(gl);
	}


	@Override
	public Tile getPosition() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(int deltaTime, Maze maze,
			ArrayList<VisibleObject> visibleObjects, Player player) {
		int plocX = maze.convertToGridX(player.getLocationX());
		int plocZ =	maze.convertToGridZ(player.getLocationZ());
		int slocX = (int) Math.floor(locationX);
		int slocZ = (int) Math.floor(locationZ);
		if (plocX==slocX){
			if (plocZ==slocZ){
				destroy = true;
				player.hp = player.hp +1;
			}
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
