import javax.media.opengl.GL;


public interface Lifeform {

	void display(GL gl);
	void update(int deltaTime, Maze maze, Player player);
	boolean isHit(Projectile p);
	
}
