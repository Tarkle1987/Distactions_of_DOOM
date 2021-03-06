package Maze;
import java.util.ArrayList;

import javax.media.opengl.GL;

import Player.Player;
import Routeplanner.Tile;

import com.sun.opengl.util.GLUT;

import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import movingobjects.VisibleObject;

import com.sun.corba.se.impl.ior.ByteBuffer;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * Maze represents the maze used by MazeRunner.
 * <p>
 * The Maze is defined as an array of integers, where 0 equals nothing and 1 equals a wall.
 * Note that the array is square and that MAZE_SIZE contains the exact length of one side.
 * This is to perform various checks to ensure that there will be no ArrayOutOfBounds 
 * exceptions and to perform the calculations needed by not only the display(GL) function, 
 * but also by functions in the MazeRunner class itself.<br />
 * Therefore it is of the utmost importance that MAZE_SIZE is correct.
 * <p>
 * SQUARE_SIZE is used by both MazeRunner and Maze itself for calculations of the 
 * display(GL) method and other functions. The larger this value, the larger the world of
 * MazeRunner will be.
 * <p>
 * This class implements VisibleObject to force the developer to implement the display(GL)
 * method, so the Maze can be displayed.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 *
 */
public class Maze implements VisibleObject {
	public final double SINGLE_SIZE = 22;
	public final double MAZE_SIZE = 2*SINGLE_SIZE;
	public final double SQUARE_SIZE = 5;
	public Texture DeurTexture,trapopTexture, trapafTexture,muurTexture, floorTexture, plafondTexture, bLinksTexture, bRechtsTexture,kRechtsTexture, kLinksTexture, portret1,portret2,portret3,portret4,portret5,portret6;
	public Texture Oranje, Rood, Blauw, Groen, Wit, Smarttex,lichaam,haar,frame,ogen,shirt,riem,broek,schoenen,bier,glas,binnenkant,guitar,rschoenen,onderbroek,rhaar;
	private int textswitch;
	public static int[][] maze = new int[44][44];
	private static int[][] textswitchArray = new int[44][44];
	
	/**
	 * Constructor, only needed for the Unit test for Routeplanner
	 * @param maze
	 */
	public Maze(){
	}

	/**
	 * isWall(int x, int z) checks for a wall.
	 * <p>
	 * It returns whether maze[x][z] contains a 1,7 or 2.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isWall( int x, int z )
	{
		boolean iswall = false;
		
		if( x >= 0 && x < MAZE_SIZE && z >= 0 && z < MAZE_SIZE ){
			
			if(maze[x][z] == 1 || maze[x][z] == 7 ||maze[x][z]==2){
				iswall = true;
			}
		}
		
		return iswall;
		
	}
	/**
	 * setWall(int x, int z) checks for a wall.
	 * <p>
	 * It returns whether maze[x][z] contains a 1.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean setWall( int x, int z){
		boolean iswall = false;
		
		if( x >= 0 && x < MAZE_SIZE && z >= 0 && z < MAZE_SIZE ){
			
			if(maze[x][z] == 1){
				iswall = true;
			}
		}
		
		return iswall;
	}
	
	/**
	 * Determines if maze[x][z] contains a 5
	 * 
	 * @param x the x-coordinate of the location to check
	 * @param z the z-coordinate of the location to check
	 * @return whether there is an end at maze[x][z]
	 */
	public boolean isEind(int x, int z){
		if( x >= 0 && x < MAZE_SIZE && z >= 0 && z < MAZE_SIZE )
			return maze[x][z] == 5;
		else
			return false;
	}
	/**
	 * Determines if maze[x][z] contains a 4
	 * 
	 * @param x the x-coordinate of the location to check
	 * @param z the z-coordinate of the location to check
	 * @return whether there is an begin at maze[x][z]
	 */
	public boolean isBegin(int x, int z){
		if( x >= 0 && x < MAZE_SIZE && z >= 0 && z < MAZE_SIZE )
			return maze[x][z] == 4;
		else
			return false;
	}
	/**
	 * Fills a 2d array with random numbers between 0 and 14 for applying walltextures randomly
	 */
	public static void fillTextMuur(){
		for (int i =0;i<44;i++){
			for (int j = 0; j<44; j++){
				if (maze[i][j]==1){
					textswitchArray[i][j]=(int) Math.ceil(Math.random()*14);
				}
			}
		}
	}
	
	public float eindI(){
		return 1;
	}
	public float eindJ(){
		return 20;
	}
	public float smartI(){
		return 15;
	}
	public float SmartJ(){
		return 15;
	}
	
	/**
	 * Method that loads all textures from image files
	 */
	public void textures(){
		try{
			InputStream stream = getClass().getResourceAsStream("Deur.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Deur.jpg"); 
			this.DeurTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Muur.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Muur.jpg"); 
			this.muurTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Vloer.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Vloer.jpg"); 
			this.floorTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Plafond.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Plafond.jpg"); 
			this.plafondTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("BiebLinks.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "BiebLinks.jpg"); 
			this.bLinksTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("BiebRechts.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "BiebRechts.jpg"); 
			this.bRechtsTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Da Vinci.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Da Vinci.jpg"); 
			this.portret1 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Darwin.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Darwin.jpg"); 
			this.portret2 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Einstein.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Einstein.jpg"); 
			this.portret3 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Galileo.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Galileo.jpg"); 
			this.portret4 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Newton.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Newton.jpg"); 
			this.portret5 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Tesla.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Tesla.jpg"); 
			this.portret6 = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Kamerrechts.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Kamerrechts.jpg"); 
			this.kRechtsTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		try{
			InputStream stream = getClass().getResourceAsStream("Kamerlinks.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Kamerlinks.jpg"); 
			this.kLinksTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Oranje.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Oranje.jpg"); 
			this.Oranje = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("fout");
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Rood.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Rood.jpg"); 
			this.Rood = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Groen.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Groen.jpg"); 
			this.Groen = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Blauw.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Blauw.jpg"); 
			this.Blauw = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Wit.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Wit.jpg"); 
			this.Wit = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("Smarttex.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Smarttex.jpg"); 
			this.Smarttex = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}	
		try{
			InputStream stream = getClass().getResourceAsStream("TrapNeer.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "TrapNeer.jpg"); 
			this.trapafTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("TrapOp.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "TrapOp.jpg"); 
			this.trapopTexture = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Roze.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Roze.jpg"); 
			this.lichaam = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Bruin.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Bruin.jpg"); 
			this.schoenen = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Bier.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Bier.jpg"); 
			this.bier = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Glas.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Glas.jpg"); 
			this.glas = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Haar.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Haar.jpg"); 
			this.haar = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("Zwart.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "Zwart.jpg"); 
			this.frame = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		try{
			InputStream stream = getClass().getResourceAsStream("RBinnenkant.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "RBinnenkant.jpg"); 
			this.binnenkant = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("RGuitar.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "RGuitar.jpg"); 
			this.guitar = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("RSchoenen.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "RSchoenen.jpg"); 
			this.rschoenen = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("ROnderbroek.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "ROnderbroek.jpg"); 
			this.onderbroek = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		try{
			InputStream stream = getClass().getResourceAsStream("RHaar.jpg");
			TextureData data = TextureIO.newTextureData(stream, false, "RHaar.jpg"); 
			this.rhaar = TextureIO.newTexture(data);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
	}
	/**
	 * Sets maze arrays from container temp to active level
	 * @param temp Mazes class containing maze arrays
	 */
	public static void setMaze(Mazes temp) {
		for (int i = 0; i<22;i++){
			for (int j = 0;j<22;j++){
				maze[i][j]=temp.getArray(1)[i][j];
			}
		}
		for (int i = 0; i<22;i++){
			for (int j = 22;j<44;j++){
				maze[i][j]=temp.getArray(2)[i][j-22];
			}
		}
		for (int i = 22; i<44;i++){
			for (int j = 0;j<22;j++){
				maze[i][j]=temp.getArray(3)[i-22][j];
			}
		}
		for (int i = 22; i<44;i++){
			for (int j = 22;j<44;j++){
				maze[i][j]=0;
			}
		}
		fillTextMuur();
	}
	
	/**
	 * isWall(double x, double z) checks for a wall by converting the double values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the grid 
	 * defined by maze[][]. Then it calls upon isWall(int, int) to check for a wall.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isWall( double x, double z )
	{
		int gX = convertToGridX( x );
		int gZ = convertToGridZ( z );
		return isWall( gX, gZ );
	}
	/**
	 * setWall(double x, double z) checks for a wall by converting the double values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the grid 
	 * defined by maze[][]. Then it calls upon isWall(int, int) to check for a wall.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean setWall(double x, double z){
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return setWall(gX,gZ);
	}
	/**
	 * isEind(double x, double z) checks for an end by converting the double values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the grid 
	 * defined by maze[][]. Then it calls upon isEind(int, int) to check for an end.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isEind( double x, double z )
	{
		int gX = convertToGridX( x );
		int gZ = convertToGridZ( z );
		return isEind( gX, gZ );
	}
	/**
	 * isBegin(double x, double z) checks for an end by converting the double values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the grid 
	 * defined by maze[][]. Then it calls upon isBegin(int, int) to check for a Begin.
	 * 
	 * @param x		the x-coordinate of the location to check
	 * @param z		the z-coordinate of the location to check
	 * @return		whether there is a wall at maze[x][z]
	 */
	public boolean isBegin( double x, double z )
	{
		int gX = convertToGridX( x );
		int gZ = convertToGridZ( z );
		return isBegin( gX, gZ );
	}
	/**
	 * Searches for the x and z coordinates of stairs in Maze array and returns them in an array
	 * @param Maze Int Array to check
	 * @return Int array with [x1,z1,x2,z2]
	 */
	public static int[] CoordTrap(int[][] Maze){
		int[] res = new int[4];
		int count = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 2){
					res[0+count] = j;
					res[1+count] = i;
					count = count+2;
				}
			}
		}
		return res;
	}
	/**
	 * Searches for the x and z coordinates of stairs down in Maze array and returns them in an array
	 * @param Maze Int Array to check
	 * @return Int array with [x1,z1,x2,z2]
	 */
	public static int[] CoordTrapaf(int[][] Maze){
		int[] res = new int[4];
		int count = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 6){
					res[0+count] = j;
					res[1+count] = i;
					count = count+2;
				}
			}
		}
		return res;
	}
	/**
	 * Searches for the x and z coordinates of smartdrugs in Maze array and returns them in an array
	 * @param Maze Int Array to check
	 * @return Int array with [x1,z1,x2,z2,.....]
	 */
	public static int[] CoordSmart(int[][] Maze){
		int aantal = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 3){
					aantal = aantal+1;
				}
			}
		}
		int[] res = new int[2*aantal+1];
		res[0]= aantal;
		int count = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 3){
					res[1+count] = j;
					res[2+count] = i;
					count = count+2;
				}
			}
		}
		return res;
	}
	
	/**
	 * Searches for the x and z coordinates of Sliding door in Maze array and returns them in an array
	 * @param Maze Int Array to check
	 * @return Int array with [x1,z1,x2,z2,.....]
	 */
	public static int[] CoordSchuifMuur(int[][] Maze){
		int aantal = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 7){
					aantal = aantal+1;
				}
			}
		}
		int[] res = new int[2*aantal+1];
		res[0]= aantal;
		int count = 0;
		for (int i = 0; i<Maze.length; i++){
			for (int j = 0; j<Maze.length; j++){
				if (Maze[j][i] == 7){
					res[1+count] = j;
					res[2+count] = i;
					count = count+2;
				}
			}
		}
		return res;
	}
	 
	/**
	 * Converts the double x-coordinate to its correspondent integer coordinate.
	 * @param x		the double x-coordinate
	 * @return		the integer x-coordinate
	 */
	public int convertToGridX( double x )
	{
		return (int)Math.floor( x / SQUARE_SIZE );
	}
	/**
	 * Converts the integer x-coordinate to its correspondent double coordinate.
	 * @param x		the integer x-coordinate
	 * @return		the double x-coordinate
	 */
	public double convertFromGridX( int x ){
		return x * SQUARE_SIZE;
	}

	/**
	 * Converts the double z-coordinate to its correspondent integer coordinate.
	 * @param z		the double z-coordinate
	 * @return		the integer z-coordinate
	 */
	public int convertToGridZ( double z )
	{
		return (int)Math.floor( z / SQUARE_SIZE );
	}
	/**
	 * Converts the integer Z-coordinate to its correspondent double coordinate.
	 * @param z		the integer Z-coordinate
	 * @return		the double Z-coordinate
	 */
	public double convertFromGridZ( int z ){
		return z*SQUARE_SIZE;
	}
	/**
	 * FUnction that displays the maze
	 */
	public void display(GL gl) {

        // Setting the wall colour and material.
        // draw the grid with the current material
      
		paintWall(gl);
		paintSingleFloorTile( gl, MAZE_SIZE );	// Paint the floor.
		paintSingleRoofTile(gl,MAZE_SIZE,SQUARE_SIZE);
	
	}
	
	/**
	 * paintWall places and randomly texturizes the walls in the maze
	 * 
	 * @param gl	the GL context in which should be drawn
	 */
	private void paintWall(GL gl)
	{
		float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };				// The walls are purple.
        gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);	// Set the materials used by the wall.
		for( int i = 0; i < MAZE_SIZE; i++ )
		{
	        for( int j = 0; j < MAZE_SIZE; j++ )
			{
	        	gl.glEnable(GL.GL_TEXTURE_2D);
	        	gl.glPushMatrix();
				gl.glTranslated( i * SQUARE_SIZE + SQUARE_SIZE / 2, SQUARE_SIZE / 2, j * SQUARE_SIZE + SQUARE_SIZE / 2 );
				if ( setWall(i, j)){
					if (i==23&&j==21){
					}
					else if (i==22&&j==20){				
					}
					else if (i==21&&j==1){
					}
					else if (i==20&&j==0){
					}
					else{
						textswitch = textswitchArray[i][j];
						switch (textswitch){
						case 1: 
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 2:
							portret1.enable();
							portret1.bind();
							break;
						case 3:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 4:
							portret2.enable();
							portret2.bind();
							break;
						case 5:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 6:
							portret3.enable();
							portret3.bind();
							break;
						case 7:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 8:
							portret4.enable();
							portret4.bind();
							break;
						case 9:
							portret5.enable();
							portret5.bind();
							break;
						case 10:
							portret6.enable();
							portret6.bind();
							break;
						case 11:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 12:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 13:
							muurTexture.enable();
							muurTexture.bind();
							break;
						case 14:
							muurTexture.enable();
							muurTexture.bind();
							break;
						}

				
						
		            gl.glBegin(GL.GL_QUADS);
		            float size = (float) SQUARE_SIZE;
		            final float[] frontUL = {(float) (-0.5*size),(float) (0.5*size),(float) (0.5*size)};
		            final float[] frontUR = {(float) (0.5*size),(float) (0.5*size),(float) (0.5*size)};
		            final float[] frontLR = {(float) (0.5*size),(float) (-0.5*size),(float) (0.5*size)};
		            final float[] frontLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (0.5*size)};
		            final float[] backUL = {(float) (-0.5*size),(float) (0.5*size),(float) (-0.5*size)};
		            final float[] backUR = {(float) (0.5*size),(float) (0.5*size),(float) (-0.5*size)};
		            final float[] backLR = {(float) (0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
		            final float[] backLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
		            // Front Face.
		            gl.glNormal3f(0.0f, 0.0f, 1.0f);
		            gl.glTexCoord2f(0.0f, 0.0f);
		            gl.glVertex3fv(frontUR, 0);
		            gl.glTexCoord2f(1.0f, 0.0f);
		            gl.glVertex3fv(frontUL, 0);
		            gl.glTexCoord2f(1.0f, 1.0f);
		            gl.glVertex3fv(frontLL, 0);
		            gl.glTexCoord2f(0.0f, 1.0f);
		            gl.glVertex3fv(frontLR, 0);
		            // Back Face.
		            gl.glNormal3f(0.0f, 0.0f, -1.0f);
		            gl.glTexCoord2f(0.0f, 0.0f);
		            gl.glVertex3fv(backUL, 0);
		            gl.glTexCoord2f(1.0f, 0.0f);
		            gl.glVertex3fv(backUR, 0);
		            gl.glTexCoord2f(1.0f, 1.0f);
		            gl.glVertex3fv(backLR, 0);
		            gl.glTexCoord2f(0.0f, 1.0f);
		            gl.glVertex3fv(backLL, 0);
		            // right face
		            gl.glNormal3f(1.0f, 0.0f, 0.0f);
		            gl.glTexCoord2f(0.0f, 0.0f);
		            gl.glVertex3fv(backUR, 0);
		            gl.glTexCoord2f(1.0f, 0.0f);
		            gl.glVertex3fv(frontUR, 0);
		            gl.glTexCoord2f(1.0f, 1.0f);
		            gl.glVertex3fv(frontLR, 0);
		            gl.glTexCoord2f(0.0f, 1.0f);
		            gl.glVertex3fv(backLR, 0);
		         // left face
		            gl.glNormal3f(-1.0f, 0.0f, 0.0f);
		            gl.glTexCoord2f(0.0f, 0.0f);
		            gl.glVertex3fv(frontUL, 0);
		            gl.glTexCoord2f(1.0f, 0.0f);
		            gl.glVertex3fv(backUL, 0);
		            gl.glTexCoord2f(1.0f, 1.0f);
		            gl.glVertex3fv(backLL, 0);
		            gl.glTexCoord2f(0.0f, 1.0f);
		            gl.glVertex3fv(frontLL, 0);
		            gl.glEnd(); 
					}
				}
				if (isEind(i,j)){
		
					bRechtsTexture.enable();
					bRechtsTexture.bind();
					gl.glBegin(GL.GL_QUADS);
			            float size = (float) SQUARE_SIZE;
			            final float[] frontUL = {(float) (-0.5*size),(float) (0.5*size),(float) (0.5*size)};
			            final float[] frontUR = {(float) (0.5*size),(float) (0.5*size),(float) (0.5*size)};
			            final float[] frontLR = {(float) (0.5*size),(float) (-0.5*size),(float) (0.5*size)};
			            final float[] frontLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (0.5*size)};
			            final float[] backUL = {(float) (-0.5*size),(float) (0.5*size),(float) (-0.5*size)};
			            final float[] backUR = {(float) (0.5*size),(float) (0.5*size),(float) (-0.5*size)};
			            final float[] backLR = {(float) (0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
			            final float[] backLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
			         // Back Face.
			            gl.glNormal3f(0.0f, 0.0f, -1.0f);
			            gl.glTexCoord2f(0.0f, 0.0f);
			            gl.glVertex3fv(frontUL, 0);
			            gl.glTexCoord2f(1.0f, 0.0f);
			            gl.glVertex3fv(frontUR, 0);
			            gl.glTexCoord2f(1.0f, 1.0f);
			            gl.glVertex3fv(frontLR, 0);
			            gl.glTexCoord2f(0.0f, 1.0f);
			            gl.glVertex3fv(frontLL, 0);
			            gl.glEnd();
			        bLinksTexture.enable();
			        bLinksTexture.bind();
			        gl.glBegin(GL.GL_QUADS);
			     // right face
		            gl.glNormal3f(1.0f, 0.0f, 0.0f);
		            gl.glTexCoord2f(0.0f, 0.0f);
		            gl.glVertex3fv(backUL, 0);
		            gl.glTexCoord2f(1.0f, 0.0f);
		            gl.glVertex3fv(frontUL, 0);
		            gl.glTexCoord2f(1.0f, 1.0f);
		            gl.glVertex3fv(frontLL, 0);
		            gl.glTexCoord2f(0.0f, 1.0f);
		            gl.glVertex3fv(backLL, 0);
			            gl.glEnd();  
				}
				if (isBegin(i,j)){
					kRechtsTexture.enable();
					kRechtsTexture.bind();
					gl.glBegin(GL.GL_QUADS);
			            float size = (float) SQUARE_SIZE;
			            final float[] frontUL = {(float) (-0.5*size),(float) (0.5*size),(float) (0.5*size)};
			            final float[] frontUR = {(float) (0.5*size),(float) (0.5*size),(float) (0.5*size)};
			            final float[] frontLR = {(float) (0.5*size),(float) (-0.5*size),(float) (0.5*size)};
			            final float[] frontLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (0.5*size)};
			            final float[] backUL = {(float) (-0.5*size),(float) (0.5*size),(float) (-0.5*size)};
			            final float[] backUR = {(float) (0.5*size),(float) (0.5*size),(float) (-0.5*size)};
			            final float[] backLR = {(float) (0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
			            final float[] backLL = {(float) (-0.5*size),(float) (-0.5*size),(float) (-0.5*size)};
			         // rechter plaatje
			            gl.glNormal3f(0.0f, 0.0f, -1.0f);
			            gl.glTexCoord2f(0.0f, 0.0f);
			            gl.glVertex3fv(backUR, 0);
			            gl.glTexCoord2f(1.0f, 0.0f);
			            gl.glVertex3fv(backUL, 0);
			            gl.glTexCoord2f(1.0f, 1.0f);
			            gl.glVertex3fv(backLL, 0);
			            gl.glTexCoord2f(0.0f, 1.0f);
			            gl.glVertex3fv(backLR, 0);
			            gl.glEnd();
			        kLinksTexture.enable();
			        kLinksTexture.bind();
			        	gl.glBegin(GL.GL_QUADS);
			     // Linker plaatje
			            gl.glNormal3f(1.0f, 0.0f, 0.0f);
			            gl.glTexCoord2f(0.0f, 0.0f);
			            gl.glVertex3fv(frontUR, 0);
			            gl.glTexCoord2f(1.0f, 0.0f);
			            gl.glVertex3fv(backUR, 0);
			            gl.glTexCoord2f(1.0f, 1.0f);
			            gl.glVertex3fv(backLR, 0);
			            gl.glTexCoord2f(0.0f, 1.0f);
			            gl.glVertex3fv(frontLR, 0);
			            gl.glEnd();  
				}
				gl.glPopMatrix();
			}
		}
	}
	/**
	 * paintSingleFloorTile(GL, double) paints a floor with texturized tiles.
	 * 
	 * @param gl	the GL context in which should be drawn
	 * @param size	the size of the tile
	 */
	private void paintSingleFloorTile(GL gl, double size)
	{
        // Setting the floor color and material.
        float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };				// The floor is blue.
        gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);	// Set the materials used by the floor.    
        gl.glNormal3d(0, 1, 0);

		for(int i = 0; i < size; i++)
		{
			for(int j= 0; j < size; j++)
			{
			if (maze[i][j]==6){
				trapafTexture.enable();
		        trapafTexture.bind();
			}
			else{
				 floorTexture.enable();
			     floorTexture.bind();
			}
				gl.glBegin(GL.GL_QUADS);
		        	gl.glVertex3d((0 + (i*SQUARE_SIZE)), 0, 0 + (j*SQUARE_SIZE));
			        gl.glTexCoord2f(0.0f, 0.0f);
			        gl.glVertex3d(0 + (i*SQUARE_SIZE), 0, SQUARE_SIZE + (j*SQUARE_SIZE));
			        gl.glTexCoord2f(0.0f, 1.0f);
			        gl.glVertex3d(SQUARE_SIZE + (i*SQUARE_SIZE), 0, SQUARE_SIZE + (j*SQUARE_SIZE));
			        gl.glTexCoord2f(1.0f, 1.0f);
			        gl.glVertex3d(SQUARE_SIZE + (i*SQUARE_SIZE), 0, 0 + (j*SQUARE_SIZE));
			        gl.glTexCoord2f(1.0f, 0.0f);
				gl.glEnd();	
				if (maze[i][j]==6){
					 trapafTexture.disable();
				}
				else{
					floorTexture.disable();
				}
			}
		}
	}
	/**
	 * paintSingleRoofTile(GL, double) paints a Roof with texturized tiles.
	 * 
	 * @param gl	the GL context in which should be drawn
	 * @param size	the size of the Rooftile
	 */
	private void paintSingleRoofTile(GL gl, double size,double height)
	{
		
		float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };				// The floor is blue.
	    gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);	// Set the materials used by the floor.
	    
	    
	    gl.glNormal3d(0, 1, 0);
		for(int i = 0; i < size; i++)
		{
			for(int j= 0; j < size; j++)
			{
		    if (maze[i][j]==2){
		    	trapopTexture.enable();
			    trapopTexture.bind();
				}
			else{
				plafondTexture.enable();
				plafondTexture.bind();
				}
	        gl.glBegin(GL.GL_QUADS);
	        	gl.glVertex3d(SQUARE_SIZE + (i*SQUARE_SIZE), height, 0 + (j*SQUARE_SIZE));
	            gl.glTexCoord2f(0.0f, 0.0f);
		        gl.glVertex3d(SQUARE_SIZE + (i*SQUARE_SIZE), height, SQUARE_SIZE + (j*SQUARE_SIZE));
		        gl.glTexCoord2f(0.0f, 1.0f);
		        gl.glVertex3d(0 + (i*SQUARE_SIZE), height, SQUARE_SIZE + (j*SQUARE_SIZE));
		        gl.glTexCoord2f(1.0f, 1.0f);
		        gl.glVertex3d((0 + (i*SQUARE_SIZE)), height, 0 + (j*SQUARE_SIZE));
		        gl.glTexCoord2f(1.0f, 0.0f);
			gl.glEnd();	
			if (maze[i][j]==6){
				 trapopTexture.disable();
			}
			else{
				plafondTexture.disable();
			}
		}
	}

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
