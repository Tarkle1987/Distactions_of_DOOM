package Score;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAccess
{
	private Connection con;
	
	public DatabaseAccess()
	{
		
	}
	public void connect()
	{
		String host = "//sql.ewi.tudelft.nl/DoomMinorProject"; // Site van de database
		String username = "Doom5"; //username
		String password = "Distactions"; //password
		try{
		con = DriverManager.getConnection("jdbc:mysql:"+ host, username, password );
		}
		catch(SQLException err)
		{
			System.err.println(err.getMessage());
		}
	}
	
	public String getHighScoreList()
	{
		String res = "";
		try {
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM Scores ORDER BY Scores.Score DESC LIMIT 0,10"; // Hier moet de goede sql query komen, liefst gesorteerd.
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			res = res + rs.getString(1) + ", " + rs.getInt(2) + "\n";
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return res;
	}
	
	public void setScore(String name, int score)
	{
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO Scores(Name, Score) VALUES (\"" + name +"\", " + score + ");"; // sql setting voor toevoegen van score
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
}
