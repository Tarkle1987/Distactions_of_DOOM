
import java.nio.file.Path;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Routeplanner
{
	private Path[][] paths;
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

	public static void testRoute(Maze maze)
	{
		PatternCheck patterns = new PatternCheck(maze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();

		for(int k =0; k < crosspoints.size(); k++)
		{
			switch(crosspoints.get(k).pattern)
			{
			case "A": SetAdjacensiesA(crosspoints, k, maze);
			break; 									// 4 mogelijkheden
			case "B": SetAdjacensiesB(crosspoints, k, maze);
			break; 									// 2 mogelijkheden
			case "C": SetAdjacensiesC(crosspoints, k, maze);
			break; 										// 2 mogelijkheden
			case "D": SetAdjacensiesD(crosspoints, k, maze);
			break; 										// 2 mogelijkheden
			case "E":SetAdjacensiesE(crosspoints, k, maze);
			break; 									// 2 mogelijkheden
			case "F":SetAdjacensiesF(crosspoints, k, maze);
			break; 									// 3 mogelijkheden
			case "G":SetAdjacensiesG(crosspoints, k, maze);
			break; 									// 3 mogelijkheden
			case "H":SetAdjacensiesH(crosspoints, k, maze);
			break; 									// 3 mogelijkheden
			case "I":SetAdjacensiesI(crosspoints, k, maze);
			break;										// 3 mogelijkheden
			/*
			case "J":SetAdjacensiesJ(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "K":SetAdjacensiesK(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "L":SetAdjacensiesL(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "M":SetAdjacensiesM(crosspoints, k, maze); // 1 mogelijkheid
			break;
			 */
			default: break;
			}

		}
		System.out.println(crosspoints.size());
		Vertex[] vertices = new Vertex[crosspoints.size()];
		for(int i =0; i <crosspoints.size(); i++)
		{
			vertices[i] = crosspoints.get(i);
		}
		if(crosspoints.size() >0)
		{
			computePaths(vertices[4]);

			for (Vertex v : vertices)
			{
				System.out.println("Distance to [" + v + "]: " + v.minDistance);
				List<Vertex> path = getShortestPathTo(v);
				System.out.println("Path: " + path);
			}
		}
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

	private static void SetAdjacensiesA(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 101 
		// 000
		// 101 

		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookRight(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookLeft(crosspoints,k, maze));
	}


	private static void SetAdjacensiesB(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,maze));
	}

	private static void SetAdjacensiesC(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies, LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookLeft(crosspoints,k, maze));
	}


	private static void SetAdjacensiesD(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 10X 
		// 001
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, maze));
	}
	private static void SetAdjacensiesE(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X01 
		// 100
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,maze));
	}

	private static void SetAdjacensiesF(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X01 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,	LookRight(crosspoints,k,maze));
	}

	private static void SetAdjacensiesG(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 10X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, maze));
	}

	private static void SetAdjacensiesH(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 000
		// 101 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookDown(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, maze));
	}
	private static void SetAdjacensiesI(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 101 
		// 000
		// X1X 
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookUp(crosspoints,k, maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookRight(crosspoints,k,maze));
		crosspoints.get(k).adjacencies = addExtraAdjacencie(crosspoints.get(k).adjacencies,LookLeft(crosspoints,k, maze));
	}
	/*
	private static void SetAdjacensiesJ(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 101 
		// 101
		// X1X
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze)};
	}	
	private static void SetAdjacensiesK(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X11 
		// 100
		// X11
		crosspoints.get(k).adjacencies = new Edge[]{
			LookRight(crosspoints,k,maze)};
	}	
	private static void SetAdjacensiesL(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 101
		// 101
		crosspoints.get(k).adjacencies = new Edge[]{
			LookDown(crosspoints,k,maze)};
	}	
	private static void SetAdjacensiesM(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 11X 
		// 001
		// 11X
		crosspoints.get(k).adjacencies = new Edge[]{
			LookLeft(crosspoints,k, maze)};
	}
	 */
	private static Edge LookLeft(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//left
		for(int m = Z-1; m > 0; m--)
		{
			if(maze.maze[X][m] != 1)
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

	private static Edge LookRight(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//right
		for(int m = Z+1; m < maze.MAZE_SIZE; m++)
		{			
			if(maze.maze[X][m] != 1)
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

	private static Edge LookDown(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//Down
		for(int m = X+1; m < maze.MAZE_SIZE; m++)
		{
			if(maze.maze[m][Z] != 1)
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

	private static Edge LookUp(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//up
		for(int m = X-1; m > 0; m--)
		{
			if(maze.maze[m][Z] != 1)
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
