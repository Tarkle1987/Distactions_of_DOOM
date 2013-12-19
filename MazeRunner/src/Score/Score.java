package Score;

import java.util.NoSuchElementException;

public class Score 
{
	private String total;
	private String[] divided = new String[10];
	public Score()
	{
		try{
			DatabaseAccess currentDB = new DatabaseAccess();
			total = currentDB.getHighScoreList();
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
	}
}
