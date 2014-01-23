package Maze;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Arraylist container for Mazes
 * @author Tark
 *
 */
public class Mazescont {

	private ArrayList<Mazes> cont;
	
	/**
	 * constructor which makes arraylist for mazes
	 */
	public Mazescont(){
		cont = new ArrayList<Mazes>();
	}
	/**
	 * Adds mazes m to arraylist
	 * @param m
	 */
	public void Add(Mazes m){
		cont.add(m);
	}
	/**
	 * 
	 * @return returns arraylist of Mazes
	 */
	public ArrayList<Mazes> getArray(){
		return cont;
	}
	/**
	 * 
	 * @param i index of Mazes to be returned
	 * @return returns mazes at position i
	 */
	public Mazes getMazes(int i){
		return cont.get(i);
	}
	/**
	 * Reads and constucts a Mazecont from a text file
	 * @param infile file te be read
	 * @return returns a Mazecont read from file
	 */
	public static Mazescont read(String infile){
		File readt= new File(infile);
		Scanner sc = null;
		Mazescont res = new Mazescont();
		try {
			 sc = new Scanner(readt);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch (InputMismatchException e){
			System.out.println("Other input expected");
		}
		while (sc.hasNext()){
			Mazes resm = Mazes.read(sc);
			res.Add(resm);
			
		}
		return res;
	}

}
