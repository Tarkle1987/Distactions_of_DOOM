package Routeplanner;
/**
 * This class is used as a crosspoint. 
 * @author Paul de Goffau
 * It contains the position, pattern and the connections of the crosspoint.
 * If computePaths is called in Routeplanner, the previous Vertex is set(In fact, this is the next)
 * It contains functions to compare(needed for computePaths(sorted list)), toString() and getters.
 */
class Vertex implements Comparable<Vertex>
{
	public final Tile tile;
	public final String pattern;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
/**
 * Constructs the crosspoint with a position and a pattern.
 * @param tile The position
 * @param pattern the pattern(see class PatternCheck)
 */
	public Vertex(Tile tile, String pattern) 
	{ 
		this.tile = tile; 
		this.pattern = pattern;
	}
/**
 * Resembles the position and pattern to a String
 * @return A String representation of the position and the pattern
 */
	public String toString() 
	{ 
		return (this.tile.toString() + " " + this.pattern); 
	}
/**
 * Compares the distance from this crosspoint to the destination with the distance from another crosspoint to the destination.
 */
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
