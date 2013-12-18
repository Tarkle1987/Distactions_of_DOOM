package NotDefined;


public class Tile 
{
	private double xpos;
	private double zpos;
	private double anglespeed;

	public Tile(double x, double z)
	{
		xpos = x;
		zpos = z;
		anglespeed = 0.6;
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

		Xdistance = tile.getX()-this.getX();
		Zdistance = tile.getZ()-this.getZ();
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
		return xpos + ", " + zpos;
	}

}
