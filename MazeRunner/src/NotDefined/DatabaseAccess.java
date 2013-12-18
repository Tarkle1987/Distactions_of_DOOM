package NotDefined;
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
		String host = "http://sql.ewi.tudelft.nl/blabla"; // Site van de database
		String username = "Username"; //username
		String password = "Password"; //password
		try{
		con = DriverManager.getConnection( host, username, password );
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
			String sql = "SELECT * FROM Score"; // Hier moet de goede sql query komen, liefst gesorteerd.
			ResultSet rs = stmt.executeQuery(sql);
			res = rs.getString(1) + ", " + rs.getInt(2);
			while(rs.next())
			{
			res = res + "\n" + rs.getString(1) + ", " + rs.getInt(2);
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
			String sql = "Insert " + name + " blabla" + score; // sql setting voor toevoegen van score
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
