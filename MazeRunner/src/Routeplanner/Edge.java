package Routeplanner;
/**
 * This class is used to set connections between crosspoints(vertices)
 * @author Paul de Goffau
 *
 */
class Edge
{
	public final Vertex target;
	public final double weight;
	public Edge(Vertex argTarget, double argWeight)
	{ 
		target = argTarget; 
		weight = argWeight; 
	}
}
