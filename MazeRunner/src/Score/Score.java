package Score;

import java.util.NoSuchElementException;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Score 
{
	private int newScore;
	private int bonus;
	private String total;
	private String[] divided = new String[10];
	private DatabaseAccess DB;
	
	public Score(){
		this.DB = new DatabaseAccess();
		this.DB.connect();
	}
	
	public int calculateNewScore(int hp, int TimeInSeconds){
		this.bonus = (1000- TimeInSeconds)*10;
		if(this.bonus < 0)
		{
			this.bonus = 0;
		}
		this.newScore = hp*500 + bonus;
		
		return newScore;
	}
	
	public void submitScore(String name)
	{
		DB.setScore(name, newScore);		
	}
	
	public void getHighScore()
	{
		try{
			total = DB.getHighScoreList();
			String Delimiter1 = "\n";
			divided = total.split(Delimiter1);
		}
		catch(NoSuchElementException e)
		{
			System.err.println(e);
		}
	}
	
	public void drawHighScores(GL gl, int screenWidth, int screenHeight)
	{
		getHighScore();
		int x = (int)(0.4*screenWidth);
		int y = (int)(0.9*screenHeight);
		//TODO: Drawing score on the endscreen, but first make an endscreen
		for(int i =0; i <divided.length; i++)
		{
			y = y - 20;
			String row = (i+1) + ": " + divided[i];
			drawText(gl, row, x,y);
		}

	}
	
	public void drawText(GL gl, String string, int x, int y)
	{
		int length = string.length();
		
		GLUT glut = new GLUT();
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glRasterPos2i(x, y); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_TIMES_ROMAN_24, string.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
		}
	}

	public void drawScore(GL gl, int screenWidth, int screenHeight) {
		// TODO Auto-generated method stub
		String congrats = "Congratulations, you have reached the end!";
		String yourscore = "Your score for this game is: " +this.newScore;
		
		drawText(gl, congrats, (int)(0.4*screenWidth)- 100, (int)(0.9*screenHeight) -340);
		drawText(gl, yourscore, (int)(0.4*screenWidth)- 100, (int)(0.9*screenHeight) -380);
	}

	private void drawBigText(GL gl, String congrats, int x, int y) 
	{
		int length = congrats.length();
		
		GLUT glut = new GLUT();
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glRasterPos2i(x, y); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, congrats.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
		}
	}
	
	public void setnewScore(int newscore){
		newScore = newscore;
	}
}
