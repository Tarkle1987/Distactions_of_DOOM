/**
 * The Control class is an abstract class containing only basic functionality such
 * as getters for all possible commands.
 * <p>
 * An update method is also included, demanding all subclasses to implement it. 
 * This method will be called just before any getters are called. The reason for this
 * is to allow the subclass to set all fields to the most recent input.
 * <p>
 * For the purposes of the assignment, it might seem unnecessary to have the 
 * actual user input in a separate class from this class. Indeed there is no other
 * subclass to Control other than UserInput, but this might change once some sort of
 * AI controlling of GameObjects is desired. Any AI that needs to control an object
 * in the game can use the same methods any human player would use, which makes it a 
 * lot more intuitive to program an AI.
 * 
 * @author Mattijs Driel
 * 
 */
public abstract class Control
{
	protected boolean forward = false;
	protected boolean back = false;
	protected boolean left = false;
	protected boolean right = false;
	protected boolean up = false;
	protected boolean down = false;
	protected boolean space = false;
	protected boolean pauze = false;
	protected boolean waspauzed = false;
	
	protected double dX = 0;
	protected double dY = 0;
	protected int CurrentX = 0;
	protected int CurrentY = 0;
	protected int ReleaseX = 0;
	protected int ReleaseY = 0;
	protected int PressedX = 0;
	protected int PressedY = 0;
	protected int WasPressedX = 0;
	protected int WasPressedY = 0;
	
	protected boolean hpdown = false;
	protected boolean hpup = false;
	protected boolean sprint = false;
	
	/**
	 * @return Returns true if forward motion is desired.
	 */
	public boolean getForward()
	{
		return forward;
	}
	
	/**
	 * @return Returns true if backwards motion is desired.
	 */
	public boolean getBack()
	{
		return back;
	}
	
	/**
	 * @return Returns true if left sidestepping motion is desired.
	 */
	public boolean getLeft()
	{
		return left;
	}
	
	/**
	 * @return Returns true if right sidestepping motion is desired.
	 */
	public boolean getRight()
	{
		return right;
	}
	
	public boolean getSpace(){
		return space;
	}
	
	public boolean getPauze(){
		return pauze;
	}
	public boolean getWaspauzed(){
		return waspauzed;
	}
	
	/**
	 * Gets the amount of rotation desired on the horizontal plane.
	 * @return The horizontal rotation.
	 */
	public double getdX()
	{
		return dX;
	}
	
	/**
	 * Gets the amount of rotation desired on the vertical plane.
	 * @return The vertical rotation.
	 */
	public double getdY()
	{
		return dY;
	}
	
	public int getCurrentX(){
		return CurrentX;
	}
	public int getCurrentY(){
		return CurrentY;
	}
	public int getReleaseX(){
		return ReleaseX;
	}
	public int getReleaseY(){
		return ReleaseY;
	}
	
	public void SwitchPauze(){
		if(pauze == true){
			pauze = false;
		}else if(pauze == false){
			pauze = true;
		}
	}
	public void mouseReleasedUsed(){
		ReleaseX = 0;
		ReleaseY = 0;
	}
	public void mouseReset(){
		dX = 0;
		dY = 0;
		CurrentX = 0;
		CurrentY = 0;
		ReleaseX = 0;
		ReleaseY = 0;
		PressedX = 0;
		PressedY = 0;
		WasPressedX = 0;
		WasPressedY = 0;
	}
	
	/**
	 * Updates the fields of the Control class to represent the
	 * most up-to-date values. 
	 */
	public abstract void update();
}
