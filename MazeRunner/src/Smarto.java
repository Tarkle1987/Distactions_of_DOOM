import java.util.ArrayList;

import javax.media.opengl.GL;


public class Smarto implements VisibleObject{

	private MazeObject Smarto;
	protected boolean destroy = false;
	private float size = 5;
	private double locationX,locationZ;
	
	public Smarto(float x, float z) {
		locationX = (double) x;
		locationZ = (double) z;
		Smarto = CustomMazeObject.readFromOBJ("Smartoranje.obj", 2);
		Smarto.setCor((float)(x+0.5)*size, (float)(z+0.5)*size,(float)0.2*size);
		Smarto.addColour("wit");	
	}
	
	
	public MazeObject getSmarto(){
		return Smarto;
	}
	
	public double getLocationX(){
		return locationX;
	}
	
	public double getLocationZ(){
		return locationZ;
	}


	@Override
	public void display(GL gl) {
		Smarto.display(gl);
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
				player.hp = player.hp+1;
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
