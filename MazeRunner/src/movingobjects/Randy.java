package movingobjects;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import Maze.Maze;
import Player.Player;


public class Randy extends CompanionCube {

	private MazeObject Randy;
	public MazeObject Lichaam, Haar, Gitaar, Ogen, Onderbroek, Band, Binnenkant, Schoenen, Pupil, Glas,Buckle;
	
	
	public Randy(double x, double y, double z) {
		super(x, y, z, 1.5);
		
		float multi = (float) 1.6;
		
//		Randy = CustomMazeObject.readFromOBJ("Randy.obj", (float)0.01);
//		Randy.setCor((float)(locationX), (float)(locationZ), (float)(locationY));
		
		Lichaam = CustomMazeObject.readFromOBJ("RLichaam.obj", multi);
		Lichaam.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
	
		Lichaam.addColour("roze");
		
		Haar = CustomMazeObject.readFromOBJ("RHaar.obj", multi);
		Haar.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Haar.addColour("bruin");
		
		Gitaar = CustomMazeObject.readFromOBJ("RGitaar.obj", multi);
		Gitaar.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Gitaar.addColour("zwart");
		
		Ogen = CustomMazeObject.readFromOBJ("ROgen.obj", multi);
		Ogen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Ogen.addColour("wit");
		
		Onderbroek = CustomMazeObject.readFromOBJ("ROnderbroek.obj", multi);
		Onderbroek.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Onderbroek.addColour("wit");
		
		Band = CustomMazeObject.readFromOBJ("RBand.obj", multi);
		Band.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Band.addColour("zwart");
		
		Binnenkant = CustomMazeObject.readFromOBJ("RBinnenkant.obj", multi);
		Binnenkant.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Binnenkant.addColour("wit");
		
		Schoenen = CustomMazeObject.readFromOBJ("RSchoenen.obj", multi);
		Schoenen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Schoenen.addColour("bruin");
		
		Pupil = CustomMazeObject.readFromOBJ("RPupil.obj", multi);
		Pupil.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));

		Pupil.addColour("geel");
		
	}
	
	public MazeObject getRandy(){
		return Randy;
	}
	
	public void display(GL gl){
		GLUT glut = new GLUT();		
		float[] CubeColor = { 0.1333f, 0.545f, 0.1333f, 1f };

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
		
		
		Lichaam.display(gl);
		Haar.display(gl);
		Gitaar.display(gl);
		Ogen.display(gl);
		Onderbroek.display(gl);
		Band.display(gl);
		Binnenkant.display(gl);
		Schoenen.display(gl);
		Pupil.display(gl);
	}
	public void update(int deltaTime, Maze maze, Player player){
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
		dR = 0;
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		}
		
//		Randy.setCor((float)locationX, (float)locationZ, (float)locationY);
	
		Lichaam.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Haar.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Gitaar.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Ogen.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Onderbroek.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Band.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Binnenkant.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Schoenen.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		Pupil.setCor((float)locationX, (float)locationZ, (float)(locationY-0.8));
		
		float dRotate = (float)dR;
		
		Lichaam.rotateVerticesZ( dRotate, 1, 1);
		Haar.rotateVerticesZ( dRotate, 1, 1);
		Gitaar.rotateVerticesZ( dRotate, 1, 1);
		Ogen.rotateVerticesZ( dRotate, 1, 1);
		Onderbroek.rotateVerticesZ( dRotate, 1, 1);
		Band.rotateVerticesZ( dRotate, 1, 1);
		Binnenkant.rotateVerticesZ( dRotate, 1, 1);
		Schoenen.rotateVerticesZ( dRotate, 1, 1);
		Pupil.rotateVerticesZ( dRotate, 1, 1);
	
	}
	
	
}