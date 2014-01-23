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
	List<Vertex> path = null;

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
			int[][] currentMaze = new int[23][23];
			//Opdelen van de 'grote' maze:
			currentMaze = createMaze(maze,objectTile);
			//Zoek alle kruispunten in de nieuwe maze, en verbindt deze aan elkaar:
			init(currentMaze);

			//Zoek de dichtsbijzijnde kruispunten op voor het object en de speler
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
			
			//Bereken voor alle aangrenzende kruispunten de routes en hun afstanden, sla het 
			// eerstvolgende kruispunt dat genomen moet worden op in next.
			if(vertices.length >0 && closestCrosspointsObject.size() > 0 && closestCrosspointsPlayer.size() > 0)
			{
				double min = Double.MAX_VALUE;
				int bestTar = 0;
				double totalDistance = 0.0;
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
				
				// Bereken volgende stap richting volgende kruispunt, en return deze naar het object
				int nextdir = getNextDirection(maze, next, objectTile);
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
	protected static Vertex getNextVertexTo(Vertex target, Boolean ownTile) 
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
	protected void init(int[][] currentMaze)
	{
		PatternCheck patterns = new PatternCheck(currentMaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();

		for(int k =0; k < crosspoints.size(); k++)
		{
			if(crosspoints.get(k).pattern.equals("A"))
			SetAdjacensiesA(crosspoints, k, currentMaze); // 4 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("B"))
			SetAdjacensiesB(crosspoints, k, currentMaze); // 2 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("C"))
			SetAdjacensiesC(crosspoints, k, currentMaze);// 2 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("D"))
			SetAdjacensiesD(crosspoints, k, currentMaze);// 2 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("E"))
			SetAdjacensiesE(crosspoints, k, currentMaze);// 2 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("F"))
			SetAdjacensiesF(crosspoints, k, currentMaze);// 3 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("G"))
			SetAdjacensiesG(crosspoints, k, currentMaze);// 3 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("H"))
			SetAdjacensiesH(crosspoints, k, currentMaze);// 3 mogelijkheden
			else if(crosspoints.get(k).pattern.equals("I"))
			SetAdjacensiesI(crosspoints, k, currentMaze); // 3 mogelijkheden
		}
		this.vertices = new Vertex[crosspoints.size()];
		for(int i =0; i <crosspoints.size(); i++)
		{
			this.vertices[i] = crosspoints.get(i);
		}
	}

/**
 * This method determines the next step to the right crosspoint
 * @param maze This class is needed for the convertToGid functions
 * @param next The next crosspoint
 * @param objectTile The position of the object
 * @return the next direction the object has to move(as seen from mazes.txt): 
 * 		down = 1(X negative, Z neutral)
		right = 2 (Z positive, X neutral)
		up = 3 (X positive, Z neutral)
		left = 4 (Z negative, X neutral)
 */
	protected static int getNextDirection(Maze maze, Vertex next, Tile objectTile) {
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
			
			if(Xs>22)
			{
				Xs = Xs-22;
			}
			if(Zs >22)
			{
				Zs = Zs-22;
			}
			
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
/**
 * Method to split the bigger maze in submazes
 * @param maze
 * @param objectTile
 * @return
 */
	protected static int[][] createMaze(Maze maze, Tile objectTile) {
		int[][] currentMaze = new int[22][22];
		if(maze.convertToGridX(objectTile.getX())>= 0 && 
				maze.convertToGridX(objectTile.getX()) < 23 
				&& maze.convertToGridZ(objectTile.getZ()) > 0 &&
				maze.convertToGridZ(objectTile.getZ()) < 23)
		{
			for(int i =0; i < 22; i++)
				for(int j =0; j < 22; j++)
				{
					currentMaze[i][j] = maze.maze[i][j];
				}
		}
		else
			if(maze.convertToGridX(objectTile.getX())> 0 && maze.convertToGridX(objectTile.getX()) < 23 
			&& maze.convertToGridZ(objectTile.getZ()) > 22 &&
			maze.convertToGridZ(objectTile.getZ()) < 45)
			{
				for(int i =0; i < 22; i++)
					for(int j =0; j < 22; j++)
					{
						currentMaze[i][j] = maze.maze[i][j+22];
					}
			}
			else
				if(maze.convertToGridX(objectTile.getX())> 22 && maze.convertToGridX(objectTile.getX()) < 45
				&& maze.convertToGridZ(objectTile.getZ()) > 0 &&
				maze.convertToGridZ(objectTile.getZ()) < 23)
				{
					for(int i =0; i < 22; i++)
						for(int j =0; j < 22; j++)
						{
							currentMaze[i][j] = maze.maze[i+22][j];
						}
				}else
					if(maze.convertToGridX(objectTile.getX())> 22 && maze.convertToGridX(objectTile.getX()) < 45 && 
					maze.convertToGridZ(objectTile.getZ()) > 22 && maze.convertToGridZ(objectTile.getZ()) < 45)
					{
						for(int i =0; i < 22; i++)
							for(int j =0; j < 22; j++)
							{
								currentMaze[i][j] = maze.maze[i+22][j+22];
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
	
	/**
	 * Returns the crosspoint where the object is. This method is called when the object is at
	 * a crosspoint. This method is needed to go to the next crosspoint.
	 * @param maze This class is needed for the convertToGrid methods
	 * @param vertices the list of crosspoints
	 * @param objectTile the position of the object
	 * @return the index of the crosspoint where the object is, if not found, return -1;
	 */
	protected static int getCross(Maze maze, Vertex[] vertices,Tile objectTile)
	{
		int Xs = maze.convertToGridX(objectTile.getX());
		int Zs = maze.convertToGridZ(objectTile.getZ());

		for(int k =0; k < vertices.length; k++)
		{
			if(vertices[k].getX() == Xs && vertices[k].getZ() == Zs)
				return k;
		}
		return -1;
	}
	
	/**
	 * This method checks if the object is at a crosspoint.
	 * @param maze This class is needed for the convertToGrid methods
	 * @param vertices the list of crosspoints
	 * @param objectTile the position of the object
	 * @return true if the object is at a crosspoint
	 */
	protected boolean atCrosspoint(Maze maze, Vertex[] vertices,Tile objectTile)
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
	/**
	 * This method clears the connections, to calculate multiple connections(between multiple closest crosspoints)
	 * @param vertices
	 */
	protected void clear(Vertex[] vertices) 
	{
		for(Vertex v:vertices)
		{
			v.previous = null;
			v.minDistance = Double.POSITIVE_INFINITY;
		}
	}
	/**
	 * This method determines the list of crosspoints in the environment of the object.
	 * @param maze This class is needed for the convertToGrid methods
	 * @param currentMaze the submaze(verdieping) of the object
	 * @param vertices the total list of crosspoints
	 * @param objectTile the position of the object
	 * @return the list of crosspoints
	 */
	protected static ArrayList<Vertex> closestCrosspoint(Maze maze, int[][] currentMaze ,Vertex[] vertices,Tile objectTile) {

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
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
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
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
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
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
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
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
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
	
	/**
	 * This method calculates the distances from the object to the player. (Must be used together with the closestCrosspoints method)
	 * @param maze This class is needed for the convertToGrid methods
	 * @param currentMaze the submaze(verdieping)
	 * @param vertices the list of crosspoints
	 * @param objectTile the position of the object
	 * @return A list of distances from the object to the crosspoints
	 */
	protected static ArrayList<Double> distanceToCrosspoints(Maze maze, int[][] currentMaze, Vertex[] vertices, Tile objectTile)
	{
		int X = maze.convertToGridX(objectTile.getX());
		int Z = maze.convertToGridZ(objectTile.getZ());
		ArrayList<Double> distanceToCrosspointsObject = new ArrayList<Double>();
		double max = Double.MAX_VALUE;
		if(X >22)
			if(X < 44)
				X = X -22;
			else
			{
				distanceToCrosspointsObject.add(max);
				return distanceToCrosspointsObject;
			}

		if(Z>22)
			if(Z<44)
				Z = Z-22;
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
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						double DifX = Math.abs(X-m);
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
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == m && vertices[n].getZ() == Z)
					{
						double DifX = Math.abs(m - X);
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
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						double DifZ = Math.abs(Z-m);
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
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
			{
				for(int n =0; n < vertices.length; n++)
				{
					if(vertices[n].getX() == X && vertices[n].getZ() == m)
					{
						double DifZ = Math.abs(m-Z);
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
	/**
	 * Method to determine if the player is at the same submaze as the object
	 * @param maze
	 * @param objectTile
	 * @param targetTile
	 * @return true if in the same maze
	 */
	protected boolean inTheSameMaze(Maze maze, Tile objectTile, Tile targetTile) {
		int Xs = maze.convertToGridX(objectTile.getX());
		int Xt = maze.convertToGridX(targetTile.getX());
		int Zs = maze.convertToGridZ(objectTile.getZ());
		int Zt = maze.convertToGridZ(targetTile.getZ());
		return(((Xs > 0 && Xs<22 && Xt > 0 && Xt <23)||
				(Xs >22 && Xs < 45 && Xt >22 && Xs <45))&&
				((Zs > 0 && Zs<23 && Zt > 0 && Zt <23)||
						(Zs >22 && Zs < 45 && Zt >22 && Zs <45)));
	}
	/**
	 * Method to validate a new connection between two crosspoints. 
	 * @param adjacencies
	 * @param edge
	 * @return An Edge[] that contains valid connections
	 */
	protected static Edge[] addExtraAdjacencie(Edge[] adjacencies, Edge edge)
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
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesA(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 101 
		// 000
		// 101 

		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesB(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesC(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesD(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 10X 
		// 001
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesE(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X01 
		// 100
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesF(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X01 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookRight(crosspoints,k,currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesG(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 10X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesH(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// X1X 
		// 000
		// 101 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Setting the connections for this pattern
	 * @param crosspoints List of all crosspoints
	 * @param k Index of the current crosspoint
	 * @param currentMaze The submaze(verdieping)
	 */
	protected static void SetAdjacensiesI(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		// 101 
		// 000
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,currentMaze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, currentMaze));
	}
	/**
	 * Method the looks in one direction for a next crosspoint. When there is no crosspoint found,
	 * an empty crosspoint is returned
	 * @param crosspoints The list of all crosspoints
	 * @param k the index of the current crosspoints
	 * @param currentMaze The submaze(verdieping)
	 * @return the next crosspoint in this direction
	 */
	protected static Edge LookLeft(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//left
		for(int m = Z-1; m > 0; m--)
		{
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
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
	/**
	 * Method the looks in one direction for a next crosspoint. When there is no crosspoint found,
	 * an empty crosspoint is returned
	 * @param crosspoints The list of all crosspoints
	 * @param k the index of the current crosspoints
	 * @param currentMaze The submaze(verdieping)
	 * @return the next crosspoint in this direction
	 */
	protected static Edge LookRight(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//right
		for(int m = Z+1; m < currentMaze.length; m++)
		{			
			if(currentMaze[X][m] != 1 && currentMaze[X][m] != 2 && currentMaze[X][m] != 7)
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
	/**
	 * Method the looks in one direction for a next crosspoint. When there is no crosspoint found,
	 * an empty crosspoint is returned
	 * @param crosspoints The list of all crosspoints
	 * @param k the index of the current crosspoints
	 * @param currentMaze The submaze(verdieping)
	 * @return the next crosspoint in this direction
	 */
	protected static Edge LookDown(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//Down
		for(int m = X+1; m < currentMaze.length; m++)
		{
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
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
	/**
	 * Method the looks in one direction for a next crosspoint. When there is no crosspoint found,
	 * an empty crosspoint is returned
	 * @param crosspoints The list of all crosspoints
	 * @param k the index of the current crosspoints
	 * @param currentMaze The submaze(verdieping)
	 * @return the next crosspoint in this direction
	 */
	protected static Edge LookUp(ArrayList<Vertex> crosspoints, int k, int[][] currentMaze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//up
		for(int m = X-1; m > 0; m--)
		{
			if(currentMaze[m][Z] != 1 && currentMaze[m][Z] != 2 && currentMaze[m][Z] != 7)
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
