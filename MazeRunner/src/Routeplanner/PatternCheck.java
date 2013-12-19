package Routeplanner;

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
			return ((maze[x-1][z-1] 	== 1 	|| maze[x-1][z-1] 	==7) &&  //left & up
					(maze[x-1][z] 		!= 1 	&& maze[x-1][z] 	!=7) && //up
					(maze[x-1][z+1]		== 1	|| maze[x-1][z+1] 	==7) && //right & up 
					(maze[x][z-1] 		!= 1 	&& maze[x][z-1] 	!=7) &&// left
					(maze[x][z] 		!= 1	&& maze[x][z] 		!=7) &&//center
					(maze[x][z+1] 		!= 1	&& maze[x][z+1] 	!=7) && // right
					(maze[x+1][z-1] 	== 1	|| maze[x+1][z-1] 	==7) && // left & under
					(maze[x+1][z] 		!= 1	&& maze[x+1][z] 	!=7) && //under
					(maze[x+1][z+1] 	== 1	|| maze[x+1][z+1] 	==7)); //right & under
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
			return ((maze[x-1][z] 	==	1 	|| maze[x-1][z] ==7) && //up
					(maze[x][z-1] 	== 	1 	|| maze[x][z-1] ==7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	!= 	1 	&& maze[x][z+1] !=7) && // right
					(maze[x+1][z] 	!= 	1 	&& maze[x+1][z] !=7)  //under
					); 
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
			return ((maze[x-1][z] 	==	1 	|| maze[x-1][z] ==7) && //up
					(maze[x][z-1] 	!= 	1 	&& maze[x][z-1] !=7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	== 	1 	|| maze[x][z+1] ==7) && // right
					(maze[x+1][z] 	!= 	1 	&& maze[x+1][z] !=7)); //under); 
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
					(maze[x-1][z] 	!=	1 	&& maze[x-1][z] !=7) && //up
					(maze[x][z-1] 	!= 	1 	&& maze[x][z-1] !=7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	== 	1 	|| maze[x][z+1] ==7) && // right
					(maze[x+1][z] 	== 	1 	|| maze[x+1][z] ==7)); //under
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
			return ((maze[x-1][z] 	!=	1 	&& maze[x-1][z] !=7) && //up
					(maze[x][z-1] 	== 	1 	|| maze[x][z-1] ==7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	!= 	1 	&& maze[x][z+1] !=7) && // right
					(maze[x+1][z] 	== 	1 || maze[x+1][z] 	==7)); // under
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
			return ((maze[x-1][z] 	!=	1 	&& maze[x-1][z] !=7) && //up
					(maze[x][z-1] 	== 	1 	|| maze[x][z-1] ==7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	!=	1 	&& maze[x][z+1] !=7) && // right
					(maze[x+1][z] 	!= 	1 	&& maze[x+1][z] !=7) //&& //under	
					); 
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
			return ((maze[x-1][z] 	!=	1 	&& maze[x-1][z] !=7) && //up
					(maze[x][z-1] 	!= 	1 	&& maze[x][z-1] !=7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	== 	1 	|| maze[x][z+1] ==7) && // right
					(maze[x+1][z] 	!= 	1 	&& maze[x+1][z] !=7)); //under
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
			return ((maze[x-1][z] 	==	1 	|| maze[x-1][z] == 7) && //up
					(maze[x][z-1] 	!= 	1 	&& maze[x][z-1] !=7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	!= 	1 	&& maze[x][z+1] !=7) && // right
					(maze[x+1][z] 	!= 	1 	&& maze[x+1][z] !=7) //under
					); 
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
			return ((maze[x-1][z] 	!=	1 	&& maze[x-1][z] !=7) && //up
					(maze[x][z-1] 	!= 	1 	&& maze[x][z-1] !=7) && // left
					(maze[x][z] 	!= 	1 	&& maze[x][z] 	!=7) &&//center
					(maze[x][z+1] 	!= 	1 	&& maze[x][z+1] !=7) && // right
					(maze[x+1][z] 	== 	1 	|| maze[x+1][z] == 7)); //under
		}
		else
			return false;
	}

}
