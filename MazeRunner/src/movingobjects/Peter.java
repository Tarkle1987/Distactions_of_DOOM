package movingobjects;

import javax.media.opengl.GL;

import Maze.Maze;
import Player.Player;


public class Peter extends CompanionCube {

	private MazeObject Peter;
	public MazeObject Lichaam, Haar, Frame, Ogen, Shirt, Riem, Broek, Schoenen, Bier, Glas;
	
	
	public Peter(double x, double y, double z) {
		super(x, y, z, 1.5);
		
		float multi = (float) 20;
		
//		Peter = CustomMazeObject.readFromOBJ("Peter.obj", (float)0.01);
//		Peter.setCor((float)(locationX), (float)(locationZ), (float)(locationY));
		
		Lichaam = CustomMazeObject.readFromOBJ("huid.obj", multi);
		Lichaam.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Lichaam.rotateVerticesZ(-90, 1, 1);
		Lichaam.addColour("roze");
		
		Haar = CustomMazeObject.readFromOBJ("haar.obj", multi);
		Haar.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Haar.rotateVerticesZ(-90, 1, 1);
		Haar.addColour("bruin");
		
		Frame = CustomMazeObject.readFromOBJ("frame.obj", multi);
		Frame.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Frame.rotateVerticesZ(-90, 1, 1);
		Frame.addColour("zwart");
		
		Ogen = CustomMazeObject.readFromOBJ("ogen.obj", multi);
		Ogen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Ogen.rotateVerticesZ(-90, 1, 1);
		Ogen.addColour("wit");
		
		Shirt = CustomMazeObject.readFromOBJ("shirt.obj", multi);
		Shirt.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Shirt.rotateVerticesZ(-90, 1, 1);
		Shirt.addColour("wit");
		
		Riem = CustomMazeObject.readFromOBJ("riem.obj", multi);
		Riem.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Riem.rotateVerticesZ(-90, 1, 1);
		Riem.addColour("zwart");
		
		Broek = CustomMazeObject.readFromOBJ("broek.obj", multi);
		Broek.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Broek.rotateVerticesZ(-90, 1, 1);
		Broek.addColour("groen");
		
		Schoenen = CustomMazeObject.readFromOBJ("schoenen.obj", multi);
		Schoenen.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Schoenen.rotateVerticesZ(-90, 1, 1);
		Schoenen.addColour("bruin");
		
		Bier = CustomMazeObject.readFromOBJ("bier.obj", multi);
		Bier.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Bier.rotateVerticesZ(-90, 1, 1);
		Bier.addColour("geel");
		
		Glas = CustomMazeObject.readFromOBJ("glas.obj", multi);
		Glas.setCor((float)(locationX), (float)(locationZ), (float)(locationY-2));
		Glas.rotateVerticesZ(-90, 1, 1);
		Glas.addColour("wit");
		
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
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		CubeRotate(deltaTime);
		}
		
//		Peter.setCor((float)locationX, (float)locationZ, (float)locationY);
	
		Lichaam.setCor((float)locationX, (float)locationZ, (float)locationY);
		Haar.setCor((float)locationX, (float)locationZ, (float)locationY);
		Frame.setCor((float)locationX, (float)locationZ, (float)locationY);
		Ogen.setCor((float)locationX, (float)locationZ, (float)locationY);
		Shirt.setCor((float)locationX, (float)locationZ, (float)locationY);
		Riem.setCor((float)locationX, (float)locationZ, (float)locationY);
		Broek.setCor((float)locationX, (float)locationZ, (float)locationY);
		Schoenen.setCor((float)locationX, (float)locationZ, (float)locationY);
		Bier.setCor((float)locationX, (float)locationZ, (float)locationY);
		Glas.setCor((float)locationX, (float)locationZ, (float)locationY);
	}
	
	
}
