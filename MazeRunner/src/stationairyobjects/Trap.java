package stationairyobjects;

import java.util.ArrayList;

import javax.media.opengl.GL;

import movingobjects.CustomMazeObject;
import movingobjects.MazeObject;
import movingobjects.VisibleObject;
import Maze.Maze;
import Player.Player;
import Routeplanner.Tile;


public class Trap implements VisibleObject{
	public MazeObject Kaft1,Kaft2,Kaft3,Papier;
	protected boolean transport = false;
	private float size = 5;
	private double locationX,locationZ;
	
	public Trap(float x, float z) {
		
		locationX = (double) x;
		locationZ = (double) z;
		Kaft1 = CustomMazeObject.readFromOBJ("Kaft1.obj",(float) 0.0175);
		Kaft1.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft1.rotateVerticesZ(-90, 1, 1);
		Kaft1.addColour("oranje");
		
		Kaft2 = CustomMazeObject.readFromOBJ("Kaft2.obj",(float) 0.0175);
		Kaft2.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft2.rotateVerticesZ(-90, 1, 1);
		Kaft2.addColour("oranje");
		
		Kaft3 = CustomMazeObject.readFromOBJ("Kaft3.obj",(float) 0.0175);
		Kaft3.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Kaft3.rotateVerticesZ(-90, 1, 1);
		Kaft3.addColour("oranje");
		
		Papier = CustomMazeObject.readFromOBJ("Papier.obj",(float) 0.0175);
		Papier.setCor((float)(x+1)*size, (float)(z+0.6)*size, 0);
		Papier.rotateVerticesZ(-90, 1, 1);
		Papier.addColour("wit");
		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		BufferedInputStream in = null;
//		try {
//			in = new BufferedInputStream(new FileInputStream("bird.wav"));
//		} catch (FileNotFoundException e) {
//			System.out.println("no such file");
//			e.printStackTrace();
//		}
//
//		int read;
//		byte[] buff = new byte[1024];
//		while ((read = in.read(buff)) > 0)
//		{
//		    out.write(buff, 0, read);
//		}
//		out.flush();
//		byte[] audiobyte = out.toByteArray();
//		buffer.wrap(audiobyte);
//		Soundbuf.configure(buffer, Buffer.FORMAT_STEREO16, 10000);
//		bron.setPosition(x,(float) 2.5,z);
//		bron.setBuffer(Soundbuf);
//		bron.setLooping(true);
//		bron.setGain(100);
//		bron.setReferenceDistance(20);
	
	}
	
	public double getLocationX(){
		return locationX;
	}
	
	public double getLocationZ(){
		return locationZ;
	}
	
	@Override
	public void display(GL gl) {
		// TODO Auto-generated method stu
		Kaft1.display(gl);
		Kaft2.display(gl);
		Kaft3.display(gl);
		Papier.display(gl);
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
