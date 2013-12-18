package HUD;
import javax.media.opengl.GL;


public class HealthBar {

	protected int hp;
	private float x;
	private float y;
	private Heart[] bar;
	private int size;
	
	public HealthBar(int screenHeight, int hp){
		this.hp = hp;
		this.x = 100;
		this.y = screenHeight - 100;
		bar = new Heart[3];
		bar[0] = new Heart(x,y);
		
		this.size = bar[0].getSize()*11 ;
		
		bar[1] = new Heart(x + size, y);
		bar[2] = new Heart(x + 2*size, y);
		
	}
	
	public void display(GL gl){
		update();
		
		for(int i = 0; i < 3; i++){
			bar[i].display(gl);
		}
	}
	public void update(){
		switch (hp){
		case 0:
			bar[0].setStatus(0);
			bar[1].setStatus(0);
			bar[2].setStatus(0);
			break;
		case 1:
			bar[0].setStatus(1);
			bar[1].setStatus(0);
			bar[2].setStatus(0);
			break;
		case 2:
			bar[0].setStatus(2);
			bar[1].setStatus(0);
			bar[2].setStatus(0);
			break;
		case 3:
			bar[0].setStatus(2);
			bar[1].setStatus(1);
			bar[2].setStatus(0);
			break;
		case 4:
			bar[0].setStatus(2);
			bar[1].setStatus(2);
			bar[2].setStatus(0);
			break;
		case 5:
			bar[0].setStatus(2);
			bar[1].setStatus(2);
			bar[2].setStatus(1);
			break;
		case 6:
			bar[0].setStatus(2);
			bar[1].setStatus(2);
			bar[2].setStatus(2);
			break;
		}
	}
	
}
