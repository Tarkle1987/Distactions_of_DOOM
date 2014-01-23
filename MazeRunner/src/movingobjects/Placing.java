package movingobjects;

import java.util.ArrayList;

import Maze.Maze;
import Routeplanner.Tile;

/**
 * @author Paul de Goffau
 * This class is used for the initial placing of the enemies at a level(floor).
 * This class uses a technology from Computational Intelligence: Swarm Intelligence.
 * An Array stores the likeliness that a position could be chosen. This is called the pheromoneArray.
 * Initial the walls are set to 0, and the rest of the Tiles are set to a likeliness of 10.
 * The first enemy chooses random a start position. The pheromoneArray is updated such that
 * the likeliness in the neighborhood of the enemy is decreased(linear).
 * This idea is copied from the defining of the territoria by animals like dogs.
 */
public class Placing {

	private int[][] PheromoneArray = new int[22][22];
	private ArrayList<Tile> PossibleTiles = new ArrayList<Tile>();
	private Maze maze;
	private int[][] submaze;
	
	/**
	 * Constructs a Placing class with the right submaze. Calls also method InitPheromone().
	 * @param maze the whole maze
	 * @param k parameter to determine at which level the enemies should be
	 * @param j parameter to determine at which level the enemies should be
	 */
	public Placing(Maze maze, int k, int j)
	{
		this.maze = maze;
		this.submaze = new int[22][22];
		if(k==0 && j==0)
		{
			for(int i=0; i<22; i++)
			{
				for(int m=0;m<22;m++)
				{
					this.submaze[i][m] = this.maze.maze[i][m];
				}
			}
		}
		else if(k==1 && j==0)
		{
			for(int i=0; i<22; i++)
			{
				for(int m=0;m<22;m++)
				{
					this.submaze[i][m] = this.maze.maze[i+22][m];
				}
			}
		}
		else if(k==0 && j==1)
		{
			for(int i=0; i<22; i++)
			{
				for(int m=0;m<22;m++)
				{
					this.submaze[i][m] = this.maze.maze[i][m+22];
				}
			}
		}
		else
		{
			for(int i=0; i<22; i++)
			{
				for(int m=0;m<22;m++)
				{
					this.submaze[i][m] = 1;
				}
			}
		}
		
		this.InitPheromone();
	}

	/**
	 * This method initializes the PheromoneArray, a 0 when there is a wall, and a 10 if not.
	 */
	protected void InitPheromone()
	{
		for(int i=0; i<22; i++)
		{
			for(int m=0;m<22;m++)
			{
				if(this.submaze[i][m] !=0)
					this.PheromoneArray[i][m] = 0;
				else
					this.PheromoneArray[i][m] = 10;
			}
		}
	}

	/**
	 * This method calculates the new likeliness of the placing, updates the array of Pheromone.
	 * @param x the x-position of the recent placed enemy
	 * @param z the z-position of the recent placed enemy
	 */
	protected void UpdatePheromone(int x, int z)
	{
		for(int i=0; i<22; i++)
		{
			for(int m=0; m<22; m++)
			{
				if(this.submaze[i][m] !=0)
					this.PheromoneArray[i][m] = 0;
				else
					this.PheromoneArray[i][m] = (this.PheromoneArray[i][m] -10 + (int)Math.floor((Math.sqrt(Math.abs(i-x)*Math.abs(i-x) + Math.abs(m-z)*Math.abs(m-z)))));
			}
		}
		
		for(int i=0; i<22; i++)
		{
			for(int m=0;m<22;m++)
			{
				if(this.PheromoneArray[i][m] <0)
					this.PheromoneArray[i][m] = 0;
			}
		}
	}

	/**
	 * This method updates the list of possible tiles. First, all positions are removed.
	 * After that, each Tile is added according the number of pheromone.
	 */
	protected void UpdateListOfPossibleTiles()
	{
		this.PossibleTiles.clear();
		for(int i =0; i<22; i++)
		{
			for(int m=0; m<22; m++)
			{
				int X = i;
				int Z = m;
				for(int k=0; k<this.PheromoneArray[i][m]; k++)
				{
					this.PossibleTiles.add(new Tile(X,Z));
				}
			}
		}
	}
/**
 * This method selects a random Tile from the list of Possible Tiles, and returns that Tile. 
 * Also it calls the Update functions.
 * @return The Tile at which the enemy must be placed.
 */
	public Tile SelectAccordingToPheromone()
	{
		double random = Math.random();
		this.UpdateListOfPossibleTiles();
		if(this.PossibleTiles.size()>0)
		{
			Tile nextTile = this.PossibleTiles.get((int)Math.round(random*this.PossibleTiles.size()));
			this.UpdatePheromone((int)nextTile.getX(), (int)nextTile.getZ());
			return nextTile;
		}
		else
		{
			return new Tile(100,100);
		}
	}
}
