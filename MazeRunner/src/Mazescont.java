import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Mazescont {

	private ArrayList<Mazes> cont;
	
	public Mazescont(){
		cont = new ArrayList<Mazes>();
	}
	
	public void Add(Mazes m){
		cont.add(m);
	}
	
	public ArrayList<Mazes> getArray(){
		return cont;
	}
	
	public Mazes getMazes(int i){
		return cont.get(i);
	}
	
	public static Mazescont read(String infile){
		File readt= new File(infile);
		Scanner sc = null;
		Mazescont res = new Mazescont();
		try {
			 sc = new Scanner(readt);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch (IOException e){
			System.out.println("IOException found");
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
	
	public static void main(String[] arg0){
//		Mazescont bla = read("Mazes2.txt");
//		for (int i = 0; i<bla.getArray().size();i++){
//			System.out.println(bla.getMazes(i).ToString());
//		}
	}
}
