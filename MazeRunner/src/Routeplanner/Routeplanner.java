package Routeplanner;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import Maze.Maze;
/**
 * 
 * @author Paul de Goffau
 *
 *	Class that contains functions that together calculate the shortest route between given Tiles.
 *	This code uses the Dijkstra Algorithm, and the heart of the code is based on a standard implementation,
 *	from http://www.algolist.com/code/java/Dijkstra%27s_algorithm
 */
public class Routeplanner
{
	Vertex[] vertices;
	double size;
	double range;

	public Routeplanner(double size) 
	{
		this.size = size;
		this.range = size/2 + 0.35;
	}

	/**
	 * This method has to be called to calculate the next direction, according to the right route.
	 * First, we check if the player and the object are in the same maze, if not, return 0;
	 * If true, the right sector of the maze is loaded, such that we do only the needed calculations.
	 * 
	 * Then method init() creates a set of crosspoints and their connections.
	 * After the initializations are done, the closest crosspoints for both the player and the object are
	 * calculated.
	 * 
	 *For all the possible combinations the Dijkstra Algorithm is called(computepaths)
	 *The shortest combination is selected, and the nextcrosspoint is determined by method
	 *getNextVertexTo().
	 *According to that vertex, and the vertex of the object is a new direction determined.
	 *This direction is returned.
	 * 
	 * @param maze the maze, read from mazes.txt
	 * @param objectTile the combined x and z position from the object
	 * @param targetTile the combined x and z position from the player
	 * @return the next direction for the object to come nearer to the player.
	 */
	public int getRoute(Maze maze, Tile objectTile, Tile targetTile)
	{
		if(inTheSameMaze(maze, objectTile,targetTile))
		{
			int[][] currentMaze = new int[22][22];
			currentMaze = createMaze(maze,objectTile);

			init(currentMaze);

			ArrayList<Vertex> closestCrosspointsObject = new ArrayList<Vertex>();
			ArrayList<Double> DistancesObject = new ArrayList<Double>();
			ArrayList<Vertex> closestCrosspointsPlayer = new ArrayList<Vertex>();
			ArrayList<Double> DistancesPlayer = new ArrayList<Double>();
			Boolean ownTile = false;
			if(atCrosspoint(maze,vertices,objectTile))
			{
				closestCrosspointsObject.add(vertices[getCross(maze,vertices,objectTile)]);
				DistancesObject.add(0.0);
				ownTile = true;
			}
			else
			{
				closestCrosspointsObject = closestCrosspoint(maze,currentMaze ,vertices,objectTile);
				DistancesObject = distanceToCrosspoints(maze,currentMaze ,vertices,objectTile);
			}
			if(atCrosspoint(maze,vertices,targetTile))
			{
				closestCrosspointsPlayer.add(vertices[getCross(maze,vertices,targetTile)]);
				DistancesPlayer.add(0.0);
			}
			else
			{
				closestCrosspointsPlayer = closestCrosspoint(maze,currentMaze ,vertices,targetTile);
				DistancesPlayer = distanceToCrosspoints(maze,currentMaze ,vertices,targetTile);
			}
			if(vertices.length >0 && closestCrosspointsObject.size() > 0 && closestCrosspointsPlayer.size() > 0)
			{
				double min = Double.MAX_VALUE;
				int bestTar = 0;
				double totalDistance = 0.0;
				List<Vertex> path = null;
				Vertex next = null;
				for(int k =0; k < closestCrosspointsObject.size(); k++)
				{
					Vertex closestCrosspointObject = closestCrosspointsObject.get(k);
					double distanceObj = DistancesObject.get(k);
					computePaths(closestCrosspointObject);
					for(int j =0; j < closestCrosspointsPlayer.size(); j++)
					{
						double distancePla = DistancesPlayer.get(j);
						totalDistance = closestCrosspointsPlayer.get(j).minDistance + distanceObj + distancePla;
						if(totalDistance < min)
						{
							min = totalDistance;
							bestTar = j;
							path = getShortestPathTo(closestCrosspointsPlayer.get(bestTar));
							next = getNextVertexTo(closestCrosspointsPlayer.get(bestTar), ownTile);
						}
					}
					clear(vertices);
				}
				//System.out.println("Distance from [" +closestCrosspointsObject.get(bestObj) + "] to [" + closestCrosspointsPlayer.get(bestTar) + "]: " + totalDistance);
				//System.out.println("Path: " + path);
				int nextdir = getNextDirection(maze, next, objectTile);
				//System.out.println(nextdir);
				return nextdir;
			}

		}
		return 0;
	}

	/**
	 * This is the function that finds for each vertex in the maze the shortest combination of vertices
	 * to the given vertex source.
	 * @param source the vertex from which the paths are determined
	 */
	public void computePaths(Vertex source)
	{
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();
			// Visit each edge exiting u
			if(u.adjacencies != null)
			{
				for (Edge e: u.adjacencies)
				{
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
	}
	/**
	 * This method returns the path from the source given to method computepaths to the given parameter target
	 * @param target 
	 * @return the path from the source given to method computepaths to the given parameter target
	 */
	public static List<Vertex> getShortestPathTo(Vertex target)
	{
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
		{
			path.add(vertex);
		}
		Collections.reverse(path);
		return path;
	}
	/**
	 * This method returns the next crosspoint(vertex) to which the object has to travel
	 * @param target 
	 * @param ownTile boolean value that gives the right next crosspoint when the object is at a crosspoint
	 * @return the next crosspoint(vertex) to which the object has to travel
	 */
	private static Vertex getNextVertexTo(Vertex target, Boolean ownTile) 
	{
		Vertex res = null;
		if(ownTile)
		{
			for(Vertex vertex = target; vertex.previous != null; vertex = vertex.previous)
			{
				res = vertex;
			}
		}
		else
		{
			for(Vertex vertex = target; vertex !=null; vertex = vertex.previous)
			{
				res = vertex;
			}
		}

		return res;
	}
	/**
	 * This method calls a patterncheck to find all crosspoints, and after that link them 
	 * with separated functions
	 * @param currentMaze the maze in which the player and the object are.
	 */
	public void init(int[][] currentMaze)
	{
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
		this.vertices = new Vertex[crosspoints.size()];
		for(int i =0; i <crosspoints.size(); i++)
		{
			this.vertices[i] = crosspoints.get(i);
		}
	}


	private static int getNextDirection(Maze maze, Vertex next, Tile objectTile) {
		// As seen from mazes.txt:
		// down = 1(X negatief, Z neutraal)
		// right = 2 (Z positief, X neutraal)
		// up = 3 (X positief, Z neutraal)
		// left = 4 (Z negatief, X neutraal)
		if(next != null)
		{
			int Xs = maze.convertToGridX(objectTile.getX());
			int Xt = (int) next.getX();
			int Zs = maze.convertToGridZ(objectTile.getZ());
			int Zt = (int) next.getZ();
			if((Xt-Xs > 0) && Zs == Zt)
				return 3;
			if((Xt-Xs < 0) && Zs == Zt)
				return 1;
			if((Xt == Xs) && (Zt-Zs > 0))
				return 2;
			if((Xt == Xs) &&(Zt-Zs < 0))
				return 4;
		}
		return 0;
	}

	private static int[][] createMaze(Maze maze, Tile objectTile) {
		int[][] currentMaze = new int[22][22];
		if(maze.convertToGridX(objectTile.getX())>= 0 && 
				maze.convertToGridX(objectTile.getX()) < 22 
				&& maze.convertToGridZ(objectTile.getZ()) > 0 &&
				maze.convertToGridZ(objectTile.getZ()) < 22)
		{
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
					for(int i =0; i < 22; i++)
						for(int j =0; j < 22; j++)
						{
							currentMaze[i][j] = maze.maze[i+21][j];
						}
				}else
					if(maze.convertToGridX(objectTile.getX())>= 22 && maze.convertToGridX(objectTile.getX()) < 44 && 
					maze.convertToGridZ(objectTile.getZ()) >= 22 && maze.convertToGridZ(objectTile.getZ()) < 44)
					{
						for(int i =0; i < 22; i++)
							for(int j =0; j < 22; j++)
							{
								currentMaze[i][j] = maze.maze[i+21][j+21];
							}
					}
					else
					{
						for(int i =0; i < 22; i++)
							for(int j =0; j < 22; j++)
							{
								currentMaze[i][j] = 1;
							}
					}
		return currentMaze;
	}
	private static int getCross(Maze maze, Vertex[] vertices,Tile objectTile)
	{
		int Xs = maze.convertToGridX(objectTile.getX());
		int Zs = maze.convertToGridZ(objectTile.getZ());

		for(int k =0; k < vertices.length; k++)
		{
			if(vertices[k].getX() == Xs && vertices[k].getZ() == Zs)
				return k;
		}
		return 0;
	}
	private boolean atCrosspoint(Maze maze, Vertex[] vertices,Tile objectTile)
	{
		int Xs = maze.convertToGridX(objectTile.getX());
		int Xf = maze.convertToGridX(objectTile.getX() + this.range);
		int Xb = maze.convertToGridX(objectTile.getX() - this.range);
		int Zs = maze.convertToGridZ(objectTile.getZ());
		int Zf = maze.convertToGridZ(objectTile.getZ() + this.range);
		int Zb = maze.convertToGridZ(objectTile.getZ() - this.range);

		if(Xs == Xf && Xs == Xb && Zs == Zf && Zs == Zb)
		{
			for(Vertex v : vertices)
			{
				if(v.getX() == Xs && v.getZ() == Zs)
				{
					return true;
				}
			}
		}
		return false;
	}
	private static void clear(Vertex[] vertices) 
	{
		for(Vertex v:vertices)
		{
			v.previous = null;
			v.minDistance = Double.POSITIVE_INFINITY;
		}
	}
	private static ArrayList<Vertex> closestCrosspoint(Maze maze, int[][] currentMaze ,Vertex[] vertices,Tile objectTile) {

		Vertex closestCrosspoint = new Vertex(new Tile(100,100), "not existing");
		int X = maze.convertToGridX(objectTile.getX());
		int Z = maze.convertToGridZ(objectTile.getZ());
		ArrayList<Vertex> closestCrosspointsObject = new ArrayList<Vertex>();

		if(X >22)
			if(X < 44)
				X = X -22;
			else
			{
				closestCrosspointsObject.add(closestCrosspoint);
				return closestCrosspointsObject;
			}

		if(Z>22)
			if(Z<44)
				Z = Z-22;
			else
			{
				closestCrosspointsObject.add(closestCrosspoint);
				return closestCrosspointsObject;
			}
		if(X < 0 || Z<0)
		{
			closestCrosspointsObject.add(closestCrosspoint);
			return closestCrosspointsObject;
		}


		up: for(int m = X; m >= 0; m--)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						closestCrosspointsObject.add(vertices[n]);
						break up;
					}
				}

			}
			else
				break up;
		}
		down: for(int m = X; m < 22; m++)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						closestCrosspointsObject.add(vertices[n]);
						break down;
					}
				}

			}
			else
				break down;
		}
		left :for(int m = Z; m >= 0; m--)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						closestCrosspointsObject.add(vertices[n]);
						break left;
					}
				}

			}
			else
				break left;
		}
		right :for(int m = Z; m <22; m++)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						closestCrosspointsObject.add(vertices[n]);
						break right;
					}
				}

			}
			else
				break right;
		}

		return closestCrosspointsObject;
	}
	private static ArrayList<Double> distanceToCrosspoints(Maze maze, int[][] currentMaze, Vertex[] vertices, Tile objectTile)
	{
		int X = maze.convertToGridX(objectTile.getX());
		int Z = maze.convertToGridZ(objectTile.getZ());
		ArrayList<Double> distanceToCrosspointsObject = new ArrayList<Double>();
		double max = Double.MAX_VALUE;
		if(X >21)
			if(X < 42)
				X = X -21;
			else
			{
				distanceToCrosspointsObject.add(max);
				return distanceToCrosspointsObject;
			}

		if(Z>21)
			if(Z<42)
				Z = Z-21;
			else
			{
				distanceToCrosspointsObject.add(max);
				return distanceToCrosspointsObject;
			}
		if(X < 0 || Z<0)
		{
			distanceToCrosspointsObject.add(max);
			return distanceToCrosspointsObject;
		}
		up: for(int m = X; m > 0; m--)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						double DifX = vertices[n].getX() - maze.convertToGridX(objectTile.getX());

						if(DifX < 0.0)
							DifX = DifX*-1;
						distanceToCrosspointsObject.add(DifX);
						break up;
					}
				}

			}
			else
				break up;
		}
		down: for(int m = X; m < 22; m++)
		{
			if(currentMaze[m][Z] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						double DifX = vertices[n].getX() - maze.convertToGridX(objectTile.getX());
						if(DifX < 0.0)
							DifX = DifX*-1;
						distanceToCrosspointsObject.add(DifX);
						break down;
					}
				}

			}
			else
				break down;
		}
		left :for(int m = Z; m > 0; m--)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						double DifZ = vertices[n].getZ() - maze.convertToGridZ(objectTile.getZ());
						if(DifZ < 0.0)
							DifZ = DifZ*-1;
						distanceToCrosspointsObject.add(DifZ);
						break left;
					}
				}

			}
			else
				break left;
		}
		right :for(int m = Z; m <22; m++)
		{
			if(currentMaze[X][m] != 1)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						double DifZ = vertices[n].getZ() - maze.convertToGridZ(objectTile.getZ());
						if(DifZ < 0.0)
							DifZ = DifZ*-1;
						distanceToCrosspointsObject.add(DifZ);
						break right;
					}
				}

			}
			else
				break right;
		}

		return distanceToCrosspointsObject;
	}
	private boolean inTheSameMaze(Maze maze, Tile objectTile, Tile targetTile) {
		int Xs = maze.convertToGridX(objectTile.getX());
		int Xt = maze.convertToGridX(targetTile.getX());
		int Zs = maze.convertToGridZ(objectTile.getZ());
		int Zt = maze.convertToGridZ(targetTile.getZ());
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
