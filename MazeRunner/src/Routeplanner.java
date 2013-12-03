import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class Vertex implements Comparable<Vertex>
{
	public final Tile tile;
	public final String pattern;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public Vertex(Tile tile, String pattern) { this.tile = tile; this.pattern = pattern;}
	public String toString() { return (this.tile.toString() + " " + this.pattern); }
	public int compareTo(Vertex other)
	{
		return Double.compare(this.minDistance, other.minDistance);
	}
	public double argWeight(Vertex other)
	{
		return this.tile.distance(other.tile);
	}
	public double getX()
	{
		return this.tile.getX();
	}
	public double getZ()
	{
		return this.tile.getZ();
	}
}

class Edge
{
	public final Vertex target;
	public final double weight;
	public Edge(Vertex argTarget, double argWeight)
	{ target = argTarget; weight = argWeight; }
}

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
			System.out.println(u.adjacencies.length);
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
			System.out.println(crosspoints.get(k).toString());
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
			case "J":SetAdjacensiesJ(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "K":SetAdjacensiesK(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "L":SetAdjacensiesL(crosspoints, k, maze); // 1 mogelijkheid
			break;
			case "M":SetAdjacensiesM(crosspoints, k, maze); // 1 mogelijkheid
			break;
			default: break;
			}

		}
		System.out.println(crosspoints.size());
		Vertex[] vertices = new Vertex[crosspoints.size()];
		for(int i =0; i <crosspoints.size(); i++)
		{
			vertices[i] = crosspoints.get(i);
		}
		computePaths(vertices[4]);
		for (Vertex v : vertices)
		{
			System.out.println("Distance to [" + v + "]: " + v.minDistance);
			List<Vertex> path = getShortestPathTo(v);
			System.out.println("Path: " + path);
		}
	}

	private static void SetAdjacensiesA(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 101 
		// 000
		// 101 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookDown(crosspoints,k,maze),
			LookRight(crosspoints,k,maze),
			LookLeft(crosspoints,k, maze)};
	}


	private static void SetAdjacensiesB(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookDown(crosspoints,k,maze),
			LookRight(crosspoints,k,maze)};
	}

	private static void SetAdjacensiesC(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookDown(crosspoints,k,maze),
			LookLeft(crosspoints,k, maze)};
	}


	private static void SetAdjacensiesD(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 10X 
		// 001
		// X1X 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookLeft(crosspoints,k, maze)};
	}
	private static void SetAdjacensiesE(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X01 
		// 100
		// X1X 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookRight(crosspoints,k,maze)};
	}

	private static void SetAdjacensiesF(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X01 
		// 100
		// X01 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookDown(crosspoints,k,maze),
			LookRight(crosspoints,k,maze)};
	}

	private static void SetAdjacensiesG(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 10X 
		// 001
		// 10X 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookDown(crosspoints,k,maze),
			LookLeft(crosspoints,k, maze)};
	}

	private static void SetAdjacensiesH(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// X1X 
		// 000
		// 101 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookDown(crosspoints,k,maze),
			LookRight(crosspoints,k,maze),
			LookLeft(crosspoints,k, maze)};
	}
	private static void SetAdjacensiesI(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		// 101 
		// 000
		// X1X 
		crosspoints.get(k).adjacencies = new Edge[]{
			LookUp(crosspoints,k, maze),
			LookRight(crosspoints,k,maze),
			LookLeft(crosspoints,k, maze)};
	}
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
	private static Edge LookLeft(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//left
		for(int m = Z-1; m > 0; m--)
		{
			for(int i =0; i< crosspoints.size(); i++)
			{
				if(crosspoints.get(i).getZ() == m && crosspoints.get(i).getX() == X)
				{
					System.out.println("Gevonden left!" + crosspoints.get(i).toString());
					return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
				}
			}
		}
		System.out.println("Null! Left");
		return null;
	}

	private static Edge LookRight(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//right
		for(int m = Z+1; m < maze.MAZE_SIZE; m++)
		{
			for(int i=0; i<crosspoints.size(); i++)
			{
				if(crosspoints.get(i).getZ() == m && crosspoints.get(i).getX() == X)
				{

					System.out.println("Gevonden right!" + crosspoints.get(i).toString());
					return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
				}
			}
		}
		System.out.println("Null! Right");
		return null;
	}

	private static Edge LookDown(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//Down
		for(int m = X+1; m < maze.MAZE_SIZE; m++)
		{
			for(int i=0; i < crosspoints.size(); i++)
			{
				if(crosspoints.get(i).getZ() == Z && crosspoints.get(i).getX() == m)
				{
					System.out.println("Gevonden down!" + crosspoints.get(i).toString());
					return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
				}
			}
		}
		System.out.println("Null! Down");
		return null;
	}

	private static Edge LookUp(ArrayList<Vertex> crosspoints, int k, Maze maze) {
		int X = (int)crosspoints.get(k).getX();
		int Z = (int) crosspoints.get(k).getZ();
		//up
		for(int m = X-1; m > 0; m--)
		{
			for(int i = 0; i<crosspoints.size(); i++)
			{

				if(crosspoints.get(i).getZ() == Z && crosspoints.get(i).getX() == m)
				{
					System.out.println("Gevonden up!" + crosspoints.get(i).toString());
					return new Edge(crosspoints.get(i), crosspoints.get(k).argWeight(crosspoints.get(i)));
				}
			}
		}
		System.out.println("Null! Up");
		return null;
	}

}
