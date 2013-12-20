package MenuButtons;


public class Knop {

	private int x1;
	private int x2;
	private int y1;
	private int y2;

	public Knop(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public boolean inKnop(int x, int y){
		boolean inknop = false;
		
		if(x < x2 && x > x1 && y < y1 && y > y2)
			inknop = true;
		
		return inknop;
	}
	
}
