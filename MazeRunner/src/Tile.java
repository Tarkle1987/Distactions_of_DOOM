
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
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Tile) {
			Tile that = (Tile) o;
			return this.xpos == that.xpos && this.zpos == that.zpos;
		}
		return false;
	}

	@Override
	public String toString() {
		return xpos + ", " + zpos + ";";
	}
}
