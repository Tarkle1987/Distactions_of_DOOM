package Score;

import java.util.NoSuchElementException;

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
	
	public void calculateNewScore(int hp, int TimeInSeconds){
		if(TimeInSeconds < 60)
		{
			this.bonus = 10000;
		}
		else if(TimeInSeconds < 180)
		{
			this.bonus = 1000;
		}
		else if(TimeInSeconds < 360)
		{
			this.bonus = 100;
		}
		this.newScore = hp*50 + bonus;
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
	
	public void drawScore()
	{
		//TODO: Drawing score on the endscreen, but first make an endscreen
		System.out.println(divided[0]);
	}
	
	public static void main(String[] args)
	{
		Score score = new Score();
		score.calculateNewScore(5, 109);
		score.submitScore("test");
		score.getHighScore();
		score.drawScore();
	}
}
