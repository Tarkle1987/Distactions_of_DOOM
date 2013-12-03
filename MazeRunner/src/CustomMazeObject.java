
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

public class CustomMazeObject extends MazeObject{
	
	public CustomMazeObject()
	{
		super();
	}
	
	public static MazeObject readFromOBJ(String fileName)
	{
		MazeObject res = new CustomMazeObject();
		
		try{
			Scanner sc = new Scanner(new File(fileName));
			
			while(sc.hasNextLine())
			{
				String line = sc.nextLine();
				if(line.startsWith("v"))
				{
					String coordinates[] = line.split("[ ]");
					float x = Float.parseFloat(coordinates[1]) * 10;
					float y = Float.parseFloat(coordinates[2]) * 10;
					float z = Float.parseFloat(coordinates[3]) * 10;
					res.vertices.add(new Vector3f(x, y, z));
					
				}
				if(line.startsWith("f"))
				{
					String points[] = line.split("[ ]");
					int [] face = new int[points.length - 1];
					for(int i  = 1; i < points.length; i++)
					{
						String[] point = points[i].split("[/]"); 
						face[i - 1] = Integer.parseInt(point[0]) - 1;
					}
					res.addFace(face);
				}
			}
			res.removeRedundantVertices();
			System.out.println("Read in " + res.vertices.size() + " vertices.");
			System.out.println("Read in " + res.faces.size() + " faces.");
			
			sc.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void display(GL gl) {
		// TODO Auto-generated method stub
		float wallColour[] = { 0.0f, 0.0f, 0.0f, 0.0f };	
		draw(gl, wallColour);
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
