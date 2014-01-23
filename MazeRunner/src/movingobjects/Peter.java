package movingobjects;

import javax.media.opengl.GL;
import Maze.Maze;
import Player.Player;


public class Peter extends CompanionCube {

	private MazeObject Peter;
	public MazeObject Lichaam, Haar, Frame, Ogen, Shirt, Riem, Broek, Schoenen, Bier, Glas,Buckle, Pet;
	
	
	public Peter(double x, double y, double z, int difficulty) {
		super(x, y, z, 1.5, difficulty);
		
		float multi = (float) 1.5;
		
		Lichaam = CustomMazeObject.readFromOBJ("PeterLichaam.obj", multi);
		Lichaam.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Lichaam.rotateVerticesZ(90, 1, 1);
	
		Lichaam.addColour("roze");
		
		Haar = CustomMazeObject.readFromOBJ("PeterHaar.obj", multi);
		Haar.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Haar.rotateVerticesZ(90, 1, 1);

		Haar.addColour("bruin");
		
		Frame = CustomMazeObject.readFromOBJ("Frame.obj", multi);
		Frame.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Frame.rotateVerticesZ(90, 1, 1);

		Frame.addColour("zwart");
		
		Ogen = CustomMazeObject.readFromOBJ("Ogen.obj", multi);
		Ogen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Ogen.rotateVerticesZ(90, 1, 1);

		Ogen.addColour("wit");
		
		Shirt = CustomMazeObject.readFromOBJ("Shirt.obj", multi);
		Shirt.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Shirt.rotateVerticesZ(90, 1, 1);

		Shirt.addColour("wit");
		
		Riem = CustomMazeObject.readFromOBJ("Riem.obj", multi);
		Riem.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Riem.rotateVerticesZ(90, 1, 1);

		Riem.addColour("zwart");
		
		Broek = CustomMazeObject.readFromOBJ("Broek.obj", multi);
		Broek.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Broek.rotateVerticesZ(90, 1, 1);

		Broek.addColour("groen");
		
		Schoenen = CustomMazeObject.readFromOBJ("PeterSchoenen.obj", multi);
		Schoenen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Schoenen.rotateVerticesZ(90, 1, 1);

		Schoenen.addColour("bruin");
		
		Bier = CustomMazeObject.readFromOBJ("Bier.obj", multi);
		Bier.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Bier.rotateVerticesZ(90, 1, 1);

		Bier.addColour("geel");
		
		Glas = CustomMazeObject.readFromOBJ("Glas.obj", multi);
		Glas.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Glas.rotateVerticesZ(90, 1, 1);

		Glas.addColour("wit");
		
		Buckle = CustomMazeObject.readFromOBJ("Buckle.obj", multi);
		Buckle.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Buckle.rotateVerticesZ(90, 1, 1);

		Buckle.addColour("wit");
		
	}
	
	public MazeObject getPeter(){
		return Peter;
	}
	
	public void display(GL gl){
		Lichaam.display(gl);
		Haar.display(gl);
		Frame.display(gl);
		Ogen.display(gl);
		Shirt.display(gl);
		Riem.display(gl);
		Broek.display(gl);
		Schoenen.display(gl);
		Bier.display(gl);
		Glas.display(gl);
		Buckle.display(gl);

	}
	public void update(int deltaTime, Maze maze, Player player){
		playerhit = false;
		
		double X = player.locationX;
		double Z = player.locationZ;

		stuntimer = stuntimer - deltaTime;

		if(stuntimer <= 0){
			stun = false;
		}

		dR = 0;
		if(!stun){

		// Bewegen van kubus naar player / door player
		CubeMove(deltaTime, maze, X,Z);


		// kubus roteerd naar de player
	
		
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		}
		
//		Peter.setCor((float)locationX, (float)locationZ, (float)locationY);

		Lichaam.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Haar.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Frame.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Ogen.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Shirt.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Riem.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Broek.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Schoenen.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Bier.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Glas.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		Buckle.setCor((float)(locationX - 1), (float)(locationZ - 0.9), (float)(locationY-0.8));
		
		float dRotate = -(float)dR;
		
		Lichaam.rotateVerticesZ( dRotate, 1, 1);
		Haar.rotateVerticesZ( dRotate, 1, 1);
		Frame.rotateVerticesZ( dRotate, 1, 1);
		Ogen.rotateVerticesZ( dRotate, 1, 1);
		Shirt.rotateVerticesZ( dRotate, 1, 1);
		Riem.rotateVerticesZ( dRotate, 1, 1);
		Broek.rotateVerticesZ( dRotate, 1, 1);
		Schoenen.rotateVerticesZ( dRotate, 1, 1);
		Bier.rotateVerticesZ( dRotate, 1, 1);
		Glas.rotateVerticesZ( dRotate, 1, 1);
		Buckle.rotateVerticesZ( dRotate, 1, 1);
		
	
		

	}
	
	
}
