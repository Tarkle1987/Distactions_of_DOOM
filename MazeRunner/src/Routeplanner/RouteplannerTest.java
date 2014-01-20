package Routeplanner;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Maze.Maze;

public class RouteplannerTest {

	int testMaze[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,0,0,0,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};


	@Test
	public void inTheSamemaze() {
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object1 = new Tile(1*m,3*m);
		Tile object2 = new Tile(24*m,24*m);
		Tile object3 = new Tile(25*m,4*m);
		Tile object4 = new Tile(20*m,24*m);
		Tile player1 = new Tile(16*m,3*m);
		Tile player2 = new Tile(40*m,40*m);
		Tile player3 = new Tile(24*m, 1*m);
		Tile player4 = new Tile(1*m, 30*m);
		Tile NE1 = new Tile(50*m,50*m);
		Tile NE2 = new Tile(60*m,60*m);
		assertTrue(Rp.inTheSameMaze(maze, object1, player1));
		assertTrue(Rp.inTheSameMaze(maze, object2, player2));
		assertTrue(Rp.inTheSameMaze(maze, object3, player3));
		assertTrue(Rp.inTheSameMaze(maze, object4, player4));
		assertFalse(Rp.inTheSameMaze(maze, object3, player1));
		assertFalse(Rp.inTheSameMaze(maze, object4, player1));
		assertFalse(Rp.inTheSameMaze(maze, NE1, NE2));
		assertFalse(Rp.inTheSameMaze(maze, object1, player2));
		assertFalse(Rp.inTheSameMaze(maze, object2, player1));
		assertFalse(Rp.inTheSameMaze(maze, object2, player3));
		assertFalse(Rp.inTheSameMaze(maze, object3, player4));
		assertFalse(Rp.inTheSameMaze(maze, object4, player1));
		
	}

	@Test
	public void rightDir(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object1 = new Tile(1*m,3*m);
		Tile object2 = new Tile(11*m,10*m);
		Tile object3 = new Tile(25*m,4*m);
		Tile object4 = new Tile(27*m,4*m);
		Tile object5 = new Tile(36*m,40*m);
		Tile player1 = new Tile(16*m,3*m);
		Tile player2 = new Tile(30*m,7*m);
		Tile player3 = new Tile(42.5*m,42.5*m);
		assertEquals(Rp.getRoute(maze, object1, player1), 4);
		//Verschil tussen 2 routes is 1 stap; omhoog(1) is de kortste.
		assertEquals(Rp.getRoute(maze, object2, player1), 1);
		//Not in the same maze
		assertEquals(Rp.getRoute(maze, object3, player1),0);
		//Werkt ook op de andere verdiepingen:
		assertEquals(Rp.getRoute(maze, object4, player2),3);
		//En met open velden:
		assertEquals(Rp.getRoute(maze, object5, player3),2);
	}

	@Test
	public void rightPath(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(3.5*m,1.5*m);
		Tile object2 = new Tile(1.5*m,20.5*m);
		Tile player = new Tile(7.5*m,10.5*m);
		Rp.getRoute(maze, object, player);
		assertEquals(Rp.path.toString(), "[6.0, 1.0 F, 6.0, 10.0 A]");
		Routeplanner Rp2 = new Routeplanner(1.5);
		Rp2.getRoute(maze, object2, player);
		assertEquals(Rp2.path.toString(), "[1.0, 20.0 C, 6.0, 20.0 G, 6.0, 10.0 A]");
	}

	@Test
	public void atCrosspoint(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		//Omdat maze.convertTogrid naar beneden afrond, moet de positie halverwege een hokje genomen worden
		Tile object = new Tile(1.5*m,1.5*m);
		Tile objectnear = new Tile(1.5*m-1.0, 1.5*m-1.0);
		Tile objectnearbutnotat = new Tile(1.5*m-2.5, 1.5*m-2.5);

		Tile objectnot = new Tile(1.5*m, 4.5*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		assertTrue(Rp.atCrosspoint(maze, Rp.vertices, object));
		assertTrue(Rp.atCrosspoint(maze, Rp.vertices, objectnear));
		assertFalse(Rp.atCrosspoint(maze, Rp.vertices, objectnearbutnotat));
		assertFalse(Rp.atCrosspoint(maze, Rp.vertices, objectnot));
	}

	@Test
	public void computePaths()
	{		
		// in combinatie met getNextVertexTo
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(27*m,4*m);
		Vertex test = new Vertex(new Tile(3,4), "B");
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		Rp.computePaths(Rp.vertices[0]);
		for(int i =0; i < Rp.vertices.length; i++)
		{
			//Every destination could be reached. This is not the case if the level has not this feature. then the path is null.
			assertEquals(Routeplanner.getNextVertexTo(Rp.vertices[i], false).toString(), test.toString());
		}
	}
	
	@Test
	public void getShortestPath()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(3.5*m,1.5*m);
		Tile player = new Tile(7.5*m,10.5*m);
		Vertex target = new Vertex(new Tile(6,10), "A");
		List<Vertex> test = new ArrayList<Vertex>();
		test.add(target);
		Rp.getRoute(maze, object, player);
		assertEquals(Routeplanner.getShortestPathTo(target),test);
	}
	
	@Test
	public void init(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		assertEquals(Rp.vertices.length, 13);
		assertEquals(Rp.vertices[0].adjacencies.length, 2);
		assertEquals(Rp.vertices[1].adjacencies.length, 3);
		assertEquals(Rp.vertices[2].adjacencies.length, 2);
		assertEquals(Rp.vertices[3].adjacencies.length, 3);
		assertEquals(Rp.vertices[4].adjacencies.length, 4);
		assertEquals(Rp.vertices[5].adjacencies.length, 3);
		assertEquals(Rp.vertices[6].adjacencies.length, 2);
		assertEquals(Rp.vertices[7].adjacencies.length, 2);
		assertEquals(Rp.vertices[8].adjacencies.length, 2);
		assertEquals(Rp.vertices[9].adjacencies.length, 2);
		assertEquals(Rp.vertices[10].adjacencies.length, 2);
		assertEquals(Rp.vertices[11].adjacencies.length, 3);
		assertEquals(Rp.vertices[12].adjacencies.length, 2);
	}
	
	@Test
	public void getNextDirection(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(2*m,1*m);
		//Let op: Vertex is zonder mazeSizes!
		Tile tnext = new Tile(6, 1);
		Tile tnext2 = new Tile(1,1);
		Vertex next = new Vertex(tnext, "F");
		Vertex next2 = new Vertex(tnext2,"B");
		Vertex nextonzin = new Vertex(new Tile(3,5), "G");
		assertEquals(Routeplanner.getNextDirection(maze, next, object),3);
		assertEquals(Routeplanner.getNextDirection(maze, next2, object),1);
		assertEquals(Routeplanner.getNextDirection(maze, nextonzin, object),0);
		assertEquals(Routeplanner.getNextDirection(maze, null, object),0);
	}
	
	@Test
	public void createMaze()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		int[][] subtestmaze = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,0,0,0,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,0,0,0,1,0,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
				{1,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

		};	
		int[][] createdMaze = Routeplanner.createMaze(maze, new Tile(1*maze.SQUARE_SIZE,1*maze.SQUARE_SIZE));
		assertEquals(createdMaze.length, subtestmaze.length);
		for(int i=0; i < createdMaze.length; i++)
		{
			for(int k=0; k < createdMaze[i].length; k++)
			{
				assertEquals(createdMaze[i][k],subtestmaze[i][k]);
			}
		}
		
		//Deel 2:
		int[][] subtestmaze2 = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};	
		int[][] createdMaze2 = Routeplanner.createMaze(maze, new Tile(23*maze.SQUARE_SIZE,1*maze.SQUARE_SIZE));
		assertEquals(createdMaze2.length, subtestmaze2.length);
		for(int i=0; i < createdMaze2.length; i++)
		{
			for(int k=0; k < createdMaze2[i].length; k++)
			{
				assertEquals(createdMaze2[i][k],subtestmaze2[i][k]);
			}
		}
		//Deel 3:
		int[][] subtestmaze3 = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

		};	
		int[][] createdMaze3 = Routeplanner.createMaze(maze, new Tile(23*maze.SQUARE_SIZE,23*maze.SQUARE_SIZE));
		assertEquals(createdMaze3.length, subtestmaze3.length);
		for(int i=0; i < createdMaze3.length; i++)
		{
			for(int k=0; k < createdMaze3[i].length; k++)
			{
				assertEquals(createdMaze3[i][k],subtestmaze3[i][k]);
			}
		}
		//Deel 4:
		int[][] subtestmaze4 = {
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},				
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};	
		int[][] createdMaze4 = Routeplanner.createMaze(maze, new Tile(1*maze.SQUARE_SIZE,23*maze.SQUARE_SIZE));
		assertEquals(createdMaze4.length, subtestmaze4.length);
		for(int i=0; i < createdMaze4.length; i++)
		{
			for(int k=0; k < createdMaze4[i].length; k++)
			{
				assertEquals(createdMaze4[i][k],subtestmaze4[i][k]);
			}
		}
		//Buiten de maze(voor het geval dat de speler/object buiten maze is geplaatst:
		int[][] createdMaze5 = Routeplanner.createMaze(maze, new Tile(45*maze.SQUARE_SIZE,45*maze.SQUARE_SIZE));
		for(int i =0; i < createdMaze5.length; i++)
		{
			for(int k=0; k < createdMaze5[i].length; k++)
			{
				assertEquals(createdMaze5[i][k], 1);
			}
		}
	}
	
	@Test
	public void getCross()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1.5*m,1.5*m);
		Tile object2 = new Tile(1.5*m,10.5*m);
		Tile notcross = new Tile(2.5*m,1.5*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		assertEquals(Routeplanner.getCross(maze, Rp.vertices, object),0);
		assertEquals(Routeplanner.getCross(maze, Rp.vertices, object2),1);
		assertEquals(Routeplanner.getCross(maze, Rp.vertices, notcross),-1);
	}

	@Test
	public void clear(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1.5*m,1.5*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		Rp.computePaths(Rp.vertices[0]);
		for(int i=1; i<Rp.vertices.length; i++)
		{
			assertTrue(Rp.vertices[i].minDistance < Double.POSITIVE_INFINITY);
			assertNotNull(Rp.vertices[i].previous);
		}
		Rp.clear(Rp.vertices);
		for(int i=1; i<Rp.vertices.length; i++)
		{
			assertTrue(Rp.vertices[i].minDistance == Double.POSITIVE_INFINITY);
			assertNull(Rp.vertices[i].previous);
		}
	}
	
	@Test
	public void closestCrosspoint()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(27*m,4*m);
		Tile NEO1 = new Tile(1*m,45*m);
		Tile NEO2 = new Tile(45*m, 1*m);
		Tile NEO3 = new Tile(45*m, 45*m);
		Tile NEO4 = new Tile(-1*m, 1*m);
		Tile NEO5 = new Tile(1*m, -1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		Vertex one = new Vertex(new Tile(3,4), "B");
		Vertex two = new Vertex(new Tile(6,4), "F");
		Vertex NR = new Vertex(new Tile(100,100), "not existing");
		ArrayList<Vertex> test = new ArrayList<Vertex>();
		test.add(one);
		test.add(two);
		ArrayList<Vertex> NotRight = new ArrayList<Vertex>();
		NotRight.add(NR);
		assertEquals(Routeplanner.closestCrosspoint(maze,subtestmaze ,Rp.vertices,object).toString(), test.toString());
		assertEquals(Routeplanner.closestCrosspoint(maze, subtestmaze, Rp.vertices, NEO1).toString(), NotRight.toString());
		assertEquals(Routeplanner.closestCrosspoint(maze, subtestmaze, Rp.vertices, NEO2).toString(), NotRight.toString());
		assertEquals(Routeplanner.closestCrosspoint(maze, subtestmaze, Rp.vertices, NEO3).toString(), NotRight.toString());
		assertEquals(Routeplanner.closestCrosspoint(maze, subtestmaze, Rp.vertices, NEO4).toString(), NotRight.toString());
		assertEquals(Routeplanner.closestCrosspoint(maze, subtestmaze, Rp.vertices, NEO5).toString(), NotRight.toString());
	}
	
	@Test
	public void distanceToCrosspoint(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		Routeplanner Rp = new Routeplanner(1.5);
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(27*m,4*m);
		Tile NEO1 = new Tile(1*m,45*m);
		Tile NEO2 = new Tile(45*m, 1*m);
		Tile NEO3 = new Tile(45*m, 45*m);
		Tile NEO4 = new Tile(-1*m, 1*m);
		Tile NEO5 = new Tile(1*m, -1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		Rp.init(subtestmaze);
		ArrayList<Double> test = new ArrayList<Double>();
		test.add(2.0);
		test.add(1.0);
		ArrayList<Double> notEx = new ArrayList<Double>();
		double max = Double.MAX_VALUE;
		notEx.add(max);
		assertEquals(test, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, object));
		assertEquals(notEx, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, NEO1));
		assertEquals(notEx, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, NEO2));
		assertEquals(notEx, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, NEO3));
		assertEquals(notEx, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, NEO4));
		assertEquals(notEx, Routeplanner.distanceToCrosspoints(maze, subtestmaze, Rp.vertices, NEO5));
	}
	
	@Test
	public void addExtraAdjacencie(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		Edge [] test = Routeplanner.addExtraAdjacencie(crosspoints.get(0).adjacencies,Routeplanner.LookDown(crosspoints,0,subtestmaze));
		Vertex controleCP = new Vertex(new Tile(6,1), "F");
		Edge [] controle = {new Edge(controleCP, Integer.MAX_VALUE)};
		assertEquals(test[0].target.toString(),controle[0].target.toString());
		Vertex res = new Vertex(new Tile(100, 100), "Not exist");
		Edge notEx = new Edge(res,Integer.MAX_VALUE);
		Edge[] test2 = Routeplanner.addExtraAdjacencie(crosspoints.get(0).adjacencies, notEx);
		assertNull(test2);
	}
	
	@Test
	public void setAdjacenciesA()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(4).adjacencies);
		Routeplanner.SetAdjacensiesA(crosspoints, 4, subtestmaze);
		assertEquals(crosspoints.get(4).adjacencies.length,4);
	}
	
	@Test
	public void setAdjacenciesB()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(0).adjacencies);
		Routeplanner.SetAdjacensiesB(crosspoints, 0, subtestmaze);
		assertEquals(crosspoints.get(0).adjacencies.length,2);
	}
	
	@Test
	public void setAdjacenciesC()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(2).adjacencies);
		Routeplanner.SetAdjacensiesC(crosspoints, 2, subtestmaze);
		assertEquals(crosspoints.get(2).adjacencies.length,2);
	}
	
	@Test
	public void setAdjacenciesD()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(9).adjacencies);
		Routeplanner.SetAdjacensiesD(crosspoints, 9, subtestmaze);
		assertEquals(crosspoints.get(9).adjacencies.length,2);
	}
	
	@Test
	public void setAdjacenciesE()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(8).adjacencies);
		Routeplanner.SetAdjacensiesE(crosspoints, 8, subtestmaze);
		assertEquals(crosspoints.get(8).adjacencies.length,2);
	}
	@Test
	public void setAdjacenciesF()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(3).adjacencies);
		Routeplanner.SetAdjacensiesF(crosspoints, 3, subtestmaze);
		assertEquals(crosspoints.get(3).adjacencies.length,3);
	}
	@Test
	public void setAdjacenciesG()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(5).adjacencies);
		Routeplanner.SetAdjacensiesG(crosspoints, 5, subtestmaze);
		assertEquals(crosspoints.get(5).adjacencies.length,3);
	}
	@Test
	public void setAdjacenciesH()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(1).adjacencies);
		Routeplanner.SetAdjacensiesH(crosspoints, 1, subtestmaze);
		assertEquals(crosspoints.get(1).adjacencies.length,3);
	}
	@Test
	public void setAdjacenciesI()
	{
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		assertNull(crosspoints.get(11).adjacencies);
		Routeplanner.SetAdjacensiesI(crosspoints, 11, subtestmaze);
		assertEquals(crosspoints.get(11).adjacencies.length,3);
	}
	
	@Test
	public void LookLeft(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		Vertex res = new Vertex(new Tile(1, 1), "B");
		Edge edge = new Edge(res,Integer.MAX_VALUE);
		Vertex NE = new Vertex(new Tile(100, 100), "Not exist");
		Edge notExist = new Edge(NE,Integer.MAX_VALUE);
		assertEquals(Routeplanner.LookLeft(crosspoints, 1, subtestmaze).target.toString(),edge.target.toString());
		assertEquals(Routeplanner.LookLeft(crosspoints, 0, subtestmaze).target.toString(),notExist.target.toString());
	}
	
	@Test
	public void LookRight(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		Vertex res = new Vertex(new Tile(1, 20), "C");
		Edge edge = new Edge(res,Integer.MAX_VALUE);
		Vertex NE = new Vertex(new Tile(100, 100), "Not exist");
		Edge notExist = new Edge(NE,Integer.MAX_VALUE);
		assertEquals(Routeplanner.LookRight(crosspoints, 1, subtestmaze).target.toString(),edge.target.toString());
		assertEquals(Routeplanner.LookRight(crosspoints, 2, subtestmaze).target.toString(),notExist.target.toString());
	}
	
	@Test
	public void LookDown(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		Vertex res = new Vertex(new Tile(6, 10), "A");
		Edge edge = new Edge(res,Integer.MAX_VALUE);
		Vertex NE = new Vertex(new Tile(100, 100), "Not exist");
		Edge notExist = new Edge(NE,Integer.MAX_VALUE);
		assertEquals(Routeplanner.LookDown(crosspoints, 1, subtestmaze).target.toString(),edge.target.toString());
		assertEquals(Routeplanner.LookDown(crosspoints, 8, subtestmaze).target.toString(),notExist.target.toString());
	}
	
	@Test
	public void LookUp(){
		Maze maze = new Maze();
		maze.maze = testMaze;
		double m = maze.SQUARE_SIZE;
		Tile object = new Tile(1*m,1*m);
		int[][] subtestmaze = Routeplanner.createMaze(maze, object);
		PatternCheck patterns = new PatternCheck(subtestmaze);
		ArrayList<Vertex> crosspoints = patterns.getCrossPoints();
		Vertex res = new Vertex(new Tile(1, 1), "B");
		Edge edge = new Edge(res,Integer.MAX_VALUE);
		Vertex NE = new Vertex(new Tile(100, 100), "Not exist");
		Edge notExist = new Edge(NE,Integer.MAX_VALUE);
		assertEquals(Routeplanner.LookUp(crosspoints, 3, subtestmaze).target.toString(),edge.target.toString());
		assertEquals(Routeplanner.LookUp(crosspoints, 0, subtestmaze).target.toString(),notExist.target.toString());
	}
	
}
