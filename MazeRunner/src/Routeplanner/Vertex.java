package Routeplanner;



class Vertex implements Comparable<Vertex>
{
	public final Tile tile;
	public final String pattern;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(Tile tile, String pattern) 
	{ 
		this.tile = tile; 
		this.pattern = pattern;
	}

	public String toString() 
	{ 
		return (this.tile.toString() + " " + this.pattern); 
	}

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
