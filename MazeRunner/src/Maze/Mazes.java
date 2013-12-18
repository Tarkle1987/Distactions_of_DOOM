package Maze;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Mazes {
	private int[][] mz,mz2,mz3;
	private String nm;
	
	public Mazes(int[][] m,int[][] m2,int[][] m3, String naam){
		mz = m;
		mz2 = m2;
		mz3 = m3;
		nm = naam;
	}
	
	public String getNaam(){
		return nm;
	}
	
	public int[][] getArray(int l){
		int[][] res = new int[22][22];
		if (l==1){
		res = mz;
		}
		if (l==2){
			res = mz2;
			}
		if (l==3){
			res = mz3;
			}
		return res;
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
		res = res + "level2" + " "+ this.mz2.length+"\n";
		for (int i = 0; i<this.mz2.length; i++){
			for (int j = 0; j<this.mz2.length; j++){
				res = res + mz2[i][j] + " ";
				if (j==mz2.length-1){
					res = res + "\n";
				}
			}
		}
		res=res+"level3"+ " "+ this.mz3.length+"\n";
		for (int i = 0; i<this.mz3.length; i++){
			for (int j = 0; j<this.mz3.length; j++){
				res = res + mz3[i][j] + " ";
				if (j==mz3.length-1){
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
		sc.next();
		int line2 = sc.nextInt();
		int[][] maze2 = new int[line2][line2];
		for (int i = 0; i<line;i++){
			for (int j = 0; j<line;j++){
			maze2[i][j] = sc.nextInt();
			}
		}
		
		sc.next();
		int line3 = sc.nextInt();
		int[][] maze3 = new int[line3][line3];
		for (int i = 0; i<line;i++){
			for (int j = 0; j<line;j++){
			maze3[i][j] = sc.nextInt();
			}
		}
		res = new Mazes(maze,maze2,maze3,naam);
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