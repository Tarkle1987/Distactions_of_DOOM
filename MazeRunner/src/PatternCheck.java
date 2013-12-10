
import java.util.ArrayList;


public class PatternCheck 
{
	ArrayList<Vertex> crosspoints = new ArrayList<Vertex>();
	public PatternCheck(int[][] maze)
	{
		for(int x = 1; x < maze.length; x++)
			for(int z = 1; z < maze[x].length; z++)
			{
				if(patternA(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "A");
					crosspoints.add(crosspoint);
				}				
				if(patternB(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "B");
					crosspoints.add(crosspoint);
				}				
				if(patternC(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "C");
					crosspoints.add(crosspoint);
				}				
				if(patternD(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "D");
					crosspoints.add(crosspoint);
				}				
				if(patternE(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "E");
					crosspoints.add(crosspoint);
				}				
				if(patternF(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "F");
					crosspoints.add(crosspoint);
				}				
				if(patternG(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "G");
					crosspoints.add(crosspoint);
				}				
				if(patternH(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "H");
					crosspoints.add(crosspoint);
				}				
				if(patternI(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "I");
					crosspoints.add(crosspoint);
				}
				/*
				if(patternJ(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "J");
					crosspoints.add(crosspoint);
				}				
				if(patternK(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "K");
					crosspoints.add(crosspoint);
				}				
				if(patternL(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "L");
					crosspoints.add(crosspoint);
				}				
				if(patternM(x,z,maze)) 
				{
					Vertex crosspoint = new Vertex(new Tile(x,z), "M");
					crosspoints.add(crosspoint);
				}
				*/
			}
	}
	
	public ArrayList<Vertex> getCrossPoints()
	{
		return this.crosspoints;
	}

	private boolean patternA(int x, int z, int[][] maze) {
		// 101 
		// 000
		// 101

		if( x >= 1 && x < maze.length-1 && z >= 1 && z < maze.length -1 )
		{
			return ((maze[x-1][z-1] 	== 1) &&  //left & up
					(maze[x-1][z] 		!=	1) && //up
					(maze[x-1][z+1]	== 1) && //right & up 
					(maze[x][z-1] 		!=	1) && // left
					(maze[x][z] 		!= 	1) &&//center
					(maze[x][z+1] 		!= 	1) && // right
					(maze[x+1][z-1] 	== 1) && // left & under
					(maze[x+1][z] 		!= 	1) && //under
					(maze[x+1][z+1] 	== 1)); //right & under
		}
		else
			return false;
	}	

	private boolean patternB(int x, int z, int[][] maze)
	{
		// X1X 
		// 100
		// X0X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return ((maze[x-1][z] 	==	1) && //up
					(maze[x][z-1] 	== 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	!= 	1) && // right
					(maze[x+1][z] 	!= 	1) //&& //under
					//(Maze.maze[x+1][z+1] == 1)
					); //right & under
		}
		else
			return false;
	}
	private boolean patternC(int x, int z, int[][] maze)
	{
		// X1X 
		// 001
		// X0X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return ((maze[x-1][z] 	==	1) && //up
					(maze[x][z-1] 	!= 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	== 	1) && // right
					//(Maze.maze[x+1][z-1] == 1) && // left & under
					(maze[x+1][z] 	!= 	1)); //under); 
		}
		else
			return false;
	}
	private boolean patternD(int x, int z, int[][] maze)
	{
		// X0X 
		// 001
		// X1X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return (//(Maze.maze[x-1][z-1] == 1) &&  //left & up
					(maze[x-1][z] 	!=	1) && //up
					(maze[x][z-1] 	!= 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	== 	1) && // right
					(maze[x+1][z] 	== 	1)); //under
		}
		else
			return false;
	}
	private boolean patternE(int x, int z,int[][] maze)
	{
		// X0X 
		// 100
		// X1X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return ((maze[x-1][z] 	!=	1) && //up
					//(Maze.maze[x-1][z+1] == 1) && //right & up 
					(maze[x][z-1] 	== 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	!= 	1) && // right
					(maze[x+1][z] 	== 	1)); // under
		}
		else
			return false;
	}
	private boolean patternF(int x, int z, int[][] maze)
	{
		// X0X 
		// 100
		// X0X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return ((maze[x-1][z] 	!=	1) && //up
					//(Maze.maze[x-1][z+1] == 1) && //right & up 
					(maze[x][z-1] 	== 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	!=	1) && // right
					(maze[x+1][z] 	!= 	1) //&& //under
					//(Maze.maze[x+1][z+1] == 1)
					); //right & under
		}
		else
			return false;
	}
	private boolean patternG(int x, int z, int[][] maze)
	{
		// X0X 
		// 001
		// X0X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return (//(Maze.maze[x-1][z-1] == 1) &&  //left & up
					(maze[x-1][z] 	!=	1) && //up
					(maze[x][z-1] 	!= 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	== 	1) && // right
					//(Maze.maze[x+1][z-1] == 1) && // left & under
					(maze[x+1][z] 	!= 	1)); //under
		}
		else
			return false;
	}
	private boolean patternH(int x, int z, int[][] maze)
	{
		// X1X 
		// 000
		// X0X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return ((maze[x-1][z] 	==	1) && //up
					(maze[x][z-1] 	!= 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	!= 	1) && // right
					//(Maze.maze[x+1][z-1] == 1) && // left & under
					(maze[x+1][z] 	!= 	1) //&& //under
					//(Maze.maze[x+1][z+1] == 1)
					); //right & under
		}
		else
			return false;
	}
	private boolean patternI(int x, int z, int[][] maze)
	{
		// X0X 
		// 000
		// X1X 
		if( x >= 1 && x < maze.length-1 && z >= 0 && z < maze.length-1 )
		{
			return (//(Maze.maze[x-1][z-1] == 1) &&  //left & up
					(maze[x-1][z] 	!=	1) && //up
					//(Maze.maze[x-1][z+1] == 1) && //right & up 
					(maze[x][z-1] 	!= 	1) && // left
					(maze[x][z] 	!= 	1) &&//center
					(maze[x][z+1] 	!= 	1) && // right
					(maze[x+1][z] 	== 	1)); //under
		}
		else
			return false;
	}
	/*
	private boolean patternJ(int x, int z, Maze maze) {
		// 101 
		// 101
		// X1X

		if( x >= 1 && x < maze.MAZE_SIZE-1 && z >= 1 && z < maze.MAZE_SIZE-1 )
		{
			return ((Maze.maze[x-1][z-1] == 1) &&  //left & up
					(Maze.maze[x-1][z] ==	0) && //up
					(Maze.maze[x-1][z+1] == 1) && //right & up 
					(Maze.maze[x][z-1] ==	1) && // left
					(Maze.maze[x][z] == 	0) &&//center
					(Maze.maze[x][z+1] == 	1) && // right
					(Maze.maze[x+1][z] == 	1)); // under
		}
		else
			return false;
	}	
	private boolean patternK(int x, int z, Maze maze) {
		// X11 
		// 100
		// X11

		if( x >= 1 && x < maze.MAZE_SIZE-1 && z >= 1 && z < maze.MAZE_SIZE-1 )
		{
			return ((Maze.maze[x-1][z] ==	1) && //up
					(Maze.maze[x-1][z+1] == 1) && //right & up 
					(Maze.maze[x][z-1] ==	1) && // left
					(Maze.maze[x][z] == 	0) &&//center
					(Maze.maze[x][z+1] == 	0) && // right
					(Maze.maze[x+1][z] == 	1) && //under
					(Maze.maze[x+1][z+1] == 1)); //right & under
		}
		else
			return false;
	}
	private boolean patternL(int x, int z, Maze maze) {
		// X1X 
		// 101
		// 101

		if( x >= 1 && x < maze.MAZE_SIZE-1 && z >= 1 && z < maze.MAZE_SIZE-1 )
		{
			return ((Maze.maze[x-1][z] ==	1) && //up
					(Maze.maze[x][z-1] ==	1) && // left
					(Maze.maze[x][z] == 	0) &&//center
					(Maze.maze[x][z+1] == 	1) && // right
					(Maze.maze[x+1][z-1] == 1) && // left & under
					(Maze.maze[x+1][z] == 	0) && //under
					(Maze.maze[x+1][z+1] == 1)); //right & under
		}
		else
			return false;
	}	
	private boolean patternM(int x, int z, Maze maze) {
		// 11X 
		// 001
		// 11X

		if( x >= 1 && x < maze.MAZE_SIZE-1 && z >= 1 && z < maze.MAZE_SIZE-1 )
		{
			return ((Maze.maze[x-1][z-1] 	== 1) &&  //left & up
					(Maze.maze[x-1][z] 		==	1) && //up
					(Maze.maze[x][z-1] 		==	0) && // left
					(Maze.maze[x][z] 		== 	0) &&//center
					(Maze.maze[x][z+1] 		== 	1) && // right
					(Maze.maze[x+1][z-1] 	== 1) && // left & under
					(Maze.maze[x+1][z] 		== 	1)); //under
		}
		else
			return false;
	}	
	*/
}
