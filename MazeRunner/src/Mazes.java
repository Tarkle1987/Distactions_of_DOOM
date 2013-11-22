import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Mazes {
	private int[][] mz;
	private String nm;
	
	public Mazes(int[][] m, String naam){
		mz = m;
		nm = naam;
	}
	
	public String getNaam(){
		return nm;
	}
	
	public int[][] getArray(){
		return mz;
	}
	
	public String ToString(){
		String res = this.nm + " " + this.mz.length+"\n";
		for (int i = 0; i<this.mz.length; i++){
			for (int j = 0; j<this.mz.length; j++){
				res = res + mz[i][j] + " ";
				if (j==mz.length-1){
					res = res + "\n";
				}
			}
		}
		return res;
	}
	
	public static Mazes read(Scanner sc){
		Mazes res = null;
		String naam = sc.next();
		int line = sc.nextInt();
		int[][] maze = new int[line][line];
		for (int i = 0; i<line;i++){
			for (int j = 0; j<line;j++){
			maze[i][j] = sc.nextInt();
			}
		}
		res = new Mazes(maze,naam);
		return res;
	}
	

	public void addtofile(String Outfile){
		Mazescont MC1 = Mazescont.read(Outfile);
		int aantal = MC1.getArray().size();
		String output = MC1.getMazes(0).ToString();
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(Outfile, false)));
			for (int j=1;j<aantal;j++){
				output = output + "\n" + MC1.getMazes(j).ToString();
			}
			output = output + "\n" + this.ToString();
			String[] lines = output.split("\n");
			for (int i = 0; i!=lines.length;i++){
				out.println(lines[i]);
			}
			out.close();
		} 
		catch (FileNotFoundException e) {
		    System.out.println("File not Found");
			}
		catch (IOException e) {
		    System.out.println("IOException found");
			}
	}
	public static void main(String[] arg0){
//		int[][] maze =
//		{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
//		{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
//		{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
//		{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
//		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
//		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};
//		Mazes bla = new Mazes(maze,"bla");
//		bla.addtofile("Mazes.txt");
//		Scanner sc = new Scanner("bla 22 \n 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n 1 0 0 0 1 0 1 0 0 0 0 0 1 0 0 0 1 0 0 0 0 1\n 1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 0 1 1 1 1\n1 0 1 0 1 0 1 0 1 0 0 0 1 0 1 0 1 0 0 0 0 1\n1 0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 1 1 0 1\n1 0 1 0 1 0 1 0 1 1 1 0 1 0 1 0 1 0 1 0 0 1\n1 0 1 0 1 0 1 0 0 0 0 0 1 0 1 0 1 0 1 1 0 1\n1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 0 1 0 0 1 0 1\n1 0 1 0 1 0 0 0 0 0 0 0 0 0 1 0 1 1 1 1 0 1\n1 0 1 0 1 0 1 0 1 1 1 1 1 1 1 0 0 0 0 0 0 1\n1 0 1 0 1 0 1 0 0 0 0 0 0 0 0 0 0 1 1 1 1 1\n1 0 1 0 1 0 1 0 1 1 1 1 1 0 1 1 1 1 0 0 0 1\n1 0 1 0 1 0 1 0 1 0 0 0 0 0 0 0 0 1 0 1 0 1\n1 0 1 0 1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 0 1\n1 0 1 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1\n1 0 1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1\n1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1\n1 0 1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n1 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1\n1 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 0 1\n1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1\n1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1\n");
//		Mazes temp = read(sc);
//		System.out.println(temp.ToString());
	}
}