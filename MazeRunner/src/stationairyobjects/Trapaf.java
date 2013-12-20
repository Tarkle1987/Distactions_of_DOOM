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
	private Maze maze;
	
	public Trapaf(float x, float z, Maze mz) {
		maze = mz;
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
		float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };				// The floor is blue.
        gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);	// Set the materials used by the floor.
        maze.trapafTexture.enable();
        maze.trapafTexture.bind();
        
        gl.glNormal3d(0, 1, 0);
	    gl.glBegin(GL.GL_QUADS);
		    	gl.glVertex3d(0, 0, 0);
		        gl.glTexCoord2f(0.0f, 0.0f);
		        gl.glVertex3d(0, 0, size);
		        gl.glTexCoord2f(0.0f, 1.0f);
		        gl.glVertex3d(size, 0, size);
		        gl.glTexCoord2f(1.0f, 1.0f);
		        gl.glVertex3d(size, 0, 0);
		        gl.glTexCoord2f(1.0f, 0.0f);
			gl.glEnd();	
			
		
		maze.trapafTexture.disable();
		
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
