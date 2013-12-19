package NotDefined;

import java.util.ArrayList;

import javax.media.opengl.GL;

import movingobjects.GameObject;
import movingobjects.VisibleObject;
import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;

public class SchuifMuur extends GameObject implements VisibleObject {

	public double size;
	public int x;
	public int z;
	public int y;
	public Maze maze;
	
	private double dLmax;
	private double dLmin;
	
	public boolean open = false;
	
	public static final byte Open = 3;
	
	static final byte Opening = 1;
	public int openingtimer = 0;
	private int openingtime = 2 * 1000;
	
	static final byte Closing = 2;
	public int closingtimer = 0;
	private int closingtime = 1 * 1000;
	
	public static final byte Closed = 0;
	public byte state = Closed;
	
	public boolean inrange = false;
	
	
	public SchuifMuur(int x, int z, Maze maze){
		super(maze.convertFromGridX(x) + maze.SQUARE_SIZE/2,  maze.SQUARE_SIZE/2, maze.convertFromGridZ(z) + maze.SQUARE_SIZE/2);
		
		size = maze.SQUARE_SIZE;
		this.x = x;
		this.y = y;
		this.z = z;
		this.maze = maze;
		this.dLmax = size;
		this.dLmin = size/2;
	
		
	}
	
	@Override
	public void display(GL gl) {
		float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };				// The walls are purple.
        gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);
    	gl.glEnable(GL.GL_TEXTURE_2D);
    	gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		
		maze.muurTexture.enable();
		maze.muurTexture.bind();
		
		gl.glBegin(GL.GL_QUADS);
          
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
       // bottom face
          gl.glNormal3f(0.0f, -1.0f, 0.0f);
          gl.glTexCoord2f(0.0f, 0.0f);
          gl.glVertex3fv(frontLL, 0);
          gl.glTexCoord2f(1.0f, 0.0f);
          gl.glVertex3fv(backLL, 0);
          gl.glTexCoord2f(1.0f, 1.0f);
          gl.glVertex3fv(backLR, 0);
          gl.glTexCoord2f(0.0f, 1.0f);
          gl.glVertex3fv(frontLR, 0);
          
          gl.glEnd(); 
		  gl.glPopMatrix();
	
	}

	@Override
	public Tile getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int deltaTime, Maze maze,
			ArrayList<VisibleObject> visibleObjects, Player player) {
		this.maze = maze;
		
		if(state == Closed || state == Open){
			double dX = player.locationX - locationX;
			double dZ = player.locationZ - locationZ;
		
			double dLength =  Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
		
			if(dLength < dLmax && dLength >= dLmin)
				inrange = true;
			else
				inrange = false;
			
			if(inrange && player.action){
				if(state == Closed){
					state = Opening;
					openingtimer = openingtime;
				}else if(state == Open){
					state = Closing;
					closingtimer = closingtime;
				}
			}
		}
		
		if(state == Opening){
			inrange = false;
			
			openingtimer = openingtimer - deltaTime;
			
			if(openingtimer <= openingtime/2)
				open = true;
			
			locationY = size*1.4 - 0.9*size *openingtimer/openingtime;
			
			if(openingtimer <= 0){
				locationY = size*1.4;
				state = Open;
				openingtimer = 0;
			}
		}else if(state == Closing){
			inrange = false;
			open = false;
			
			closingtimer = closingtimer - deltaTime;
		
			
			
			locationY = size/2 + 0.9*size * closingtimer/closingtime;
			
			if(closingtimer <= 0){
				locationY = size*.5;
				state = Closed;
				closingtimer = 0;
			}
		}
		
		
		System.out.println("state: " + state);
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
