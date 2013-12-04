
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Routeplanner
{
	public static void computePaths(Vertex source)
	{
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();
			// Visit each edge exiting u
			for (int k =0; k< u.adjacencies.length; k++)
			{
				Edge e = u.adjacencies[k];
				Vertex v = e.target;
				double weight = e.weight;
				double distanceThroughU = u.minDistance + weight;
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v);
					v.minDistance = distanceThroughU ;
					v.previous = u;
					vertexQueue.add(v);
				}
			}
		}
	}

	public static List<Vertex> getShortestPathTo(Vertex target)
	{
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex);
		Collections.reverse(path);
		return path;
	}

	public static void testRoute(Maze maze, Tile objectTile, Tile targetTile)
	{
		if(inTheSameMaze(maze, objectTile,targetTile))
		{
			int[][] currentMaze = new int[22][22];
			if(maze.convertToGridX(objectTile.getX())>= 0 && 
					maze.convertToGridX(objectTile.getX()) < 22 
					&& maze.convertToGridZ(objectTile.getZ()) > 0 &&
					maze.convertToGridZ(objectTile.getZ()) < 22)
			{
				System.out.println("Maze1");
				for(int i =0; i < 22; i++)
					for(int j =0; j < 22; j++)
					{
						currentMaze[i][j] = maze.maze[i][j];
					}
			}
			else
				if(maze.convertToGridX(objectTile.getX())>= 0 && maze.convertToGridX(objectTile.getX()) < 22 
				&& maze.convertToGridZ(objectTile.getZ()) >= 22 &&
				maze.convertToGridZ(objectTile.getZ()) < 44)
				{
					System.out.println("Maze2");
					for(int i =0; i < 22; i++)
						for(int j =0; j < 22; j++)
						{
							currentMaze[i][j] = maze.maze[i][j+21];
						}
				}
				else
					if(maze.convertToGridX(objectTile.getX())>= 22 && maze.convertToGridX(objectTile.getX()) < 44
					&& maze.convertToGridZ(objectTile.getZ()) > 0 &&
					maze.convertToGridZ(objectTile.getZ()) < 22)
					{
						System.out.println("Maze3");
						for(int i =0; i < 22; i++)
							for(int j =0; j < 22; j++)
							{
								currentMaze[i][j] = maze.maze[i+21][j];
							}
					}else
						if(maze.convertToGridX(objectTile.getX())>= 22 && maze.convertToGridX(objectTile.getX()) < 44 && 
						maze.convertToGridZ(objectTile.getZ()) >= 22 && maze.convertToGridZ(objectTile.getZ()) < 44)
						{
							System.out.println("Maze4");
							for(int i =0; i < 22; i++)
								for(int j =0; j < 22; j++)
								{
									currentMaze[i][j] = maze.maze[i+21][j+21];
								}
						}
						else
						{
							System.out.println("No maze");
							for(int i =0; i < 22; i++)
								for(int j =0; j < 22; j++)
								{
									currentMaze[i][j] = 1;
								}
						}
			PatternCheck patterns = new PatternCheck(currentMaze);
			ArrayList<Vertex> crosspoints = patterns.getCrossPoints();

			for(int k =0; k < crosspoints.size(); k++)
			{
				switch(crosspoints.get(k).pattern)
				{
				case "A": SetAdjacensiesA(crosspoints, k, currentMaze);
				break; 									// 4 mogelijkheden
				case "B": SetAdjacensiesB(crosspoints, k, currentMaze);
				break; 									// 2 mogelijkheden
				case "C": SetAdjacensiesC(crosspoints, k, currentMaze);
				break; 										// 2 mogelijkheden
				case "D": SetAdjacensiesD(crosspoints, k, currentMaze);
				break; 										// 2 mogelijkheden
				case "E":SetAdjacensiesE(crosspoints, k, currentMaze);
				break; 									// 2 mogelijkheden
				case "F":SetAdjacensiesF(crosspoints, k, currentMaze);
				break; 									// 3 mogelijkheden
				case "G":SetAdjacensiesG(crosspoints, k, currentMaze);
				break; 									// 3 mogelijkheden
				case "H":SetAdjacensiesH(crosspoints, k, currentMaze);
				break; 									// 3 mogelijkheden
				case "I":SetAdjacensiesI(crosspoints, k, currentMaze);
				break;										// 3 mogelijkheden
				default: break;
				}

			}
			Vertex[] vertices = new Vertex[crosspoints.size()];
			for(int i =0; i <crosspoints.size(); i++)
			{
				vertices[i] = crosspoints.get(i);
			}

			Vertex closestCrosspointObject = closestCrosspoint(maze,currentMaze ,vertices,objectTile);
			Vertex closestCrosspointPlayer = closestCrosspoint(maze,currentMaze ,vertices,targetTile);
			
			if(crosspoints.size() >0)
			{
				computePaths(closestCrosspointObject);
				System.out.println("Distance from [" +closestCrosspointObject + "] to [" + closestCrosspointPlayer + "]: " + closestCrosspointPlayer.minDistance);
				List<Vertex> path = getShortestPathTo(closestCrosspointPlayer);
				System.out.println("Path: " + path);
			}
		}
	}

	private static Vertex closestCrosspoint(Maze maze, int[][] currentMaze ,Vertex[] vertices,Tile objectTile) {

		double min = Double.MAX_VALUE;
		Vertex closestCrosspoint = new Vertex(new Tile(100,100), "not existing");
		int X = maze.convertToGridX(objectTile.getX());
		int Z = maze.convertToGridZ(objectTile.getZ());
		
		ArrayList<Vertex> closestCrosspointsObject = new ArrayList<Vertex>();
		ArrayList<Double> distanceToCrosspointsObject = new ArrayList<Double>();
		
		left: for(int m = X; m > 0; m--)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length -1; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						closestCrosspointsObject.add(vertices[n]);
						distanceToCrosspointsObject.add(vertices[n].tile.distance(objectTile));
						break left;
					}
				}

			}
			else
				break left;
		}
		right: for(int m = X; m < 22; m++)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length -1; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						closestCrosspointsObject.add(vertices[n]);
						distanceToCrosspointsObject.add(vertices[n].tile.distance(objectTile));
						break right;
					}
				}

			}
			else
				break right;
		}
		down :for(int m = Z; m > 0; m--)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length -1; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						closestCrosspointsObject.add(vertices[n]);
						distanceToCrosspointsObject.add(vertices[n].tile.distance(objectTile));
						break down;
					}
				}

			}
			else
				break down;
		}
		up :for(int m = Z; m <21; m++)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length -1; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						closestCrosspointsObject.add(vertices[n]);
						distanceToCrosspointsObject.add(vertices[n].tile.distance(objectTile));
						break up;
					}
				}

			}
			else
				break up;
		}
	
		for(int k =0; k<distanceToCrosspointsObject.size(); k++)
		{
			if(distanceToCrosspointsObject.get(k) < min)
			{	
				min = distanceToCrosspointsObject.get(k);
				closestCrosspoint = closestCrosspointsObject.get(k);
			}
		}
		return closestCrosspoint;
	}

	private static boolean inTheSameMaze(Maze maze, Tile objectTile, Tile targetTile) {
		int Xs = maze.convertToGridX(objectTile.getX());
		int Xt = maze.convertToGridX(targetTile.getX());
		int Zs = maze.convertToGridZ(objectTile.getZ());
		int Zt = maze.convertToGridZ(objectTile.getZ());
		return(((Xs > 0 && Xs<22 && Xt > 0 && Xt <22)||(Xs >21 && Xs < 44 && Xt >21 && Xs <44))&&
				((Zs > 0 && Zs<22 && Zt > 0 && Zt <22)||(Zs >21 && Zs < 44 && Zt >21 && Zs <44)));
	}

	public static Edge[] addExtraAdjacencie(Edge[] adjacencies, Edge edge)
	{
		Edge[] newAdjacencies;
		if(!edge.target.pattern.equals("Not exist"))
		{
			if(adjacencies != null)
			{
				newAdjacencies = new Edge[adjacencies.length +1];
				for(int k =0; k < adjacencies.length; k++)
				{
					newAdjacencies[k] = adjacencies[k];
				}
			}
			else
			{
				newAdjacencies = new Edge[1];
			}
			newAdjacencies[newAdjacencies.length-1] = edge;
			return newAdjacencies;
		}
		return adjacencies;
	}

	private static void SetAdjacensiesA(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 101 
		// 000
		// 101 

		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookLeft(crosspoints,k, currentMaze));
	}


	private static void SetAdjacensiesB(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
	}

	private static void SetAdjacensiesC(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookLeft(crosspoints,k, currentMaze));
	}


	private static void SetAdjacensiesD(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 10X 
		// 001
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	private static void SetAdjacensiesE(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X01 
		// 100
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
	}

	private static void SetAdjacensiesF(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X01 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookRight(crosspoints,k,currentMaze));
	}

	private static void SetAdjacensiesG(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 10X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}

	private static void SetAdjacensiesH(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 000
		// 101 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	private static void SetAdjacensiesI(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 101 
		// 000
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}

	private static Edge LookLeft(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//left
		for(int m = Z-1; m > 0; m--)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int i =0; i< crosspoints.size(); i++)
				{
					if(crosspoints.get(i).getZ() == m && crosspoints.get(i).getX() == X)
					{
						return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
					}
				}
			}
			else break;
		}
		Tile notExist = new Tile(100, 100);
		Vertex res = new Vertex(notExist, "Not exist");
		return new Edge(res,Integer.MAX_VALUE);
	}

	private static Edge LookRight(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//right
		for(int m = Z+1; m < currentMaze.length; m++)
		{			
			if(currentMaze[X][m] != 1)
			{
				for(int i=0; i<crosspoints.size(); i++)
				{
					if(crosspoints.get(i).getZ() == m && crosspoints.get(i).getX() == X)
					{
						return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
					}
				}
			}
			else
				break;
		}
		Tile notExist = new Tile(100, 100);
		Vertex res = new Vertex(notExist, "Not exist");
		return new Edge(res,Integer.MAX_VALUE);
	}

	private static Edge LookDown(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//Down
		for(int m = X+1; m < currentMaze.length; m++)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int i=0; i < crosspoints.size(); i++)
				{
					if(crosspoints.get(i).getZ() == Z && crosspoints.get(i).getX() == m)
					{
						return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
					}
				}
			}
			else break;
		}
		Tile notExist = new Tile(100, 100);		
		Vertex res = new Vertex(notExist, "Not exist");
		return new Edge(res,Integer.MAX_VALUE);
	}

	private static Edge LookUp(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//up
		for(int m = X-1; m > 0; m--)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int i = 0; i<crosspoints.size(); i++)
				{

					if(crosspoints.get(i).getZ() == Z && crosspoints.get(i).getX() == m)
					{
						return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
					}
				}
			}
			else
				break;
		}
		Tile notExist = new Tile(100, 100);
		Vertex res = new Vertex(notExist, "Not exist");
		return new Edge(res,Integer.MAX_VALUE);
	}

}
