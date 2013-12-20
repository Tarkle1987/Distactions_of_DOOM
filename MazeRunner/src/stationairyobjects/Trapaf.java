package stationairyobjects;

import java.io.InputStream;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;
import movingobjects.VisibleObject;


public class Trapaf implements VisibleObject{
	protected boolean transport = false;
	private float size = 5;
	private double locationX,locationZ;
	
	public Trapaf(float x, float z) {
		locationX = (double) x;
		locationZ = (double) z;
	}
	
	public double getLocationX(){
		return locationX;
	}
	
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

	@Override
	public void update(int deltaTime, Maze maze,
			ArrayList<VisibleObject> visibleObjects, Player player) {
		// TODO Auto-generated method stub
		int plocX = maze.convertToGridX(player.getLocationX());
		int plocZ =	maze.convertToGridZ(player.getLocationZ());
		int tlocX = (int) Math.floor(locationX);
		int tlocZ = (int) Math.floor(locationZ);
		if (plocX==tlocX){
			if (plocZ==tlocZ){
				transport = true;
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