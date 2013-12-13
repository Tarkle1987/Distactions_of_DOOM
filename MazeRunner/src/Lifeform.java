import javax.media.opengl.GL;


public interface Lifeform {

	
	
	void display(GL gl);
	void update(int deltaTime, Maze maze, Player player);
	boolean isHit(Projectile p);
	boolean Collide(Lifeform l);
	double getSize();
	double getLocationX();
	double getLocationZ();
	void stepBack(int deltaTime);
	boolean getPlayerHit();
	double[] getPlayerLocation();
	void SetPlayerLocation(double[] PL);
	boolean getSight();
}
