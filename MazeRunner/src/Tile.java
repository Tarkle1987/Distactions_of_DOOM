
public class Tile 
{
	private double xpos;
	private double zpos;
	
	public Tile(double x, double z)
	{
		xpos = x;
		zpos = z;
	}
	
	public double getX()
	{
		return this.xpos;
	}
	
	public double getZ()
	{
		return this.zpos;
	}
	
	public void setX(double x)
	{
		this.xpos = x;
	}
	
	public void setZ(double z)
	{
		this.zpos = z;
	}
	
	public double distance(Tile tile)
	{
		double Xdistance = 0.0;
		double Zdistance = 0.0;
		
		if(tile.getX()> this.getX())
		{Xdistance = tile.getX()-this.getX();}
		else {Xdistance = this.getX()- tile.getX();}

		if(tile.getZ() > this.getZ())
		{Zdistance = tile.getZ()-this.getZ();}
		else {Zdistance = this.getZ()-tile.getZ();}
		
		return Math.sqrt((Xdistance*Xdistance + Zdistance*Zdistance));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Tile) {
			Tile that = (Tile) o;
			return (this.xpos == that.xpos && this.zpos == that.zpos);
		}
		return false;
	}

	@Override
	public String toString() {
		return xpos + ", " + zpos + ";";
	}
}
