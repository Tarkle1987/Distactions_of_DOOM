package Routeplanner;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PatternCheckTest {

	int maze[][] =
			{
			{1,1,1,1,1,1,1,1},
			{1,0,1,0,0,0,1,1},
			{1,0,0,0,1,0,1,1},
			{0,0,1,0,0,0,1,1},
			{1,0,0,0,0,7,1,1},
			{1,1,0,2,1,1,1,1},
			{1,0,0,0,1,1,1,1},
			{1,1,0,1,1,1,1,1}
};
	
	Vertex one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen;
	Tile tone, ttwo, tthree, tfour, tfive, tsix, tseven, teight, tnine, tten, televen, ttwelve, tthirteen;

	@Test
	public void total() {
		tone = new Tile(1,3);
		ttwo = new Tile(1,5);
		tthree = new Tile(2,1);
		tfour = new Tile(2,3);
		tfive = new Tile(3,1);
		tsix = new Tile(3,3);
		tseven = new Tile(3,4);
		teight = new Tile(3,5);
		tnine = new Tile(4,1);
		tten = new Tile(4,2);
		televen = new Tile(4,3);
		ttwelve = new Tile(4,4);
		tthirteen = new Tile(6,2);
		
		one = new Vertex(tone, "B");
		two = new Vertex(ttwo, "C");
		three = new Vertex(tthree, "F");
		four = new Vertex(tfour, "G");
		five = new Vertex(tfive, "G");
		six = new Vertex(tsix, "F");
		seven = new Vertex(tseven, "H");
		eight = new Vertex(teight, "D");
		nine = new Vertex(tnine, "E");
		ten = new Vertex(tten, "H");
		eleven = new Vertex(televen, "I");
		twelve = new Vertex(ttwelve, "D");
		thirteen = new Vertex(tthirteen, "A");
		
		ArrayList<Vertex> testList = new ArrayList<Vertex>();
		testList.add(one);
		testList.add(two);
		testList.add(three);
		testList.add(four);
		testList.add(five);
		testList.add(six);
		testList.add(seven);
		testList.add(eight);
		testList.add(nine);
		testList.add(ten);
		testList.add(eleven);
		testList.add(twelve);
		testList.add(thirteen);
		PatternCheck PC = new PatternCheck(maze);
		for(int i =0; i < testList.size(); i++)
		{
			assertEquals(PC.getCrossPoints().get(i).toString(), testList.get(i).toString());
		}
	}
	
	@Test
	public void patroonA()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternA(6,2,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonB()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternB(1,3,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonC()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternC(1,5,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonD()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternD(3,5,maze));
		assertTrue(PC.patternD(4, 4, maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonE()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternE(4,1,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonF()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternF(2,1,maze));
		assertTrue(PC.patternF(3, 3, maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonG()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternG(2,3,maze));
		assertTrue(PC.patternG(3, 1, maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonH()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternH(3,4,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}
	@Test
	public void patroonI()
	{
		PatternCheck PC = new PatternCheck(maze);
		assertTrue(PC.patternI(4,3,maze));
		assertFalse(PC.patternA(7, 2, maze));
		assertFalse(PC.patternA(30,30,maze));
	}


}
