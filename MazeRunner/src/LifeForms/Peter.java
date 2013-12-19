package LifeForms;

import javax.media.opengl.GL;

import Maze.Maze;
import NotDefined.CustomMazeObject;
import NotDefined.MazeObject;
import NotDefined.Player;

public class Peter extends CompanionCube {

	private MazeObject Peter;
	
	public Peter(double x, double y, double z) {
		super(x, y, z, 1.5);
		
		Peter = CustomMazeObject.readFromOBJ("Peter.obj", (float)0.01);
		Peter.setCor((float)(locationX), (float)(locationZ), (float)(locationY));
		
	}
	
	
	public void display(GL gl){
		Peter.display(gl);
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
		
		Peter.setCor((float)locationX, (float)locationZ, (float)locationY);
	}
	
	
}
