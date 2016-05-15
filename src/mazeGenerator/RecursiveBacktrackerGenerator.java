package mazeGenerator;

import java.util.*;
import maze.*;
import graph.*;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	//Variables:
	Random r = new Random();  //Random number generator
	int numOfCells = 0;  //Number of cells in the maze
	int firstCell = 0;  //Randomly selected starting cell for DFS
	ArrayList<Cell> cells = new ArrayList<>();  //Arraylist of the maze cells
	ArrayList<Integer> DFSList = new ArrayList<>();  //Array list of DFS order
	Graph g = null;  //Graph structure to represent maze cell verticies
	boolean[] visited;  //Array of marked cells

	//Constants:
	static int[] sides;

	@Override
	public void generateMaze(Maze maze) {
		if(maze.type == Maze.HEX) {  //Hex sides:
			sides = new int[] {Maze.NORTHEAST, Maze.SOUTHEAST, Maze.NORTHWEST, Maze.SOUTHWEST, Maze.EAST, Maze.WEST};
		} else {  //Normal sides:
			sides = new int[] {Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST};
		}

		//Initialization of class values:
		numOfCells = maze.sizeR * maze.sizeC;

		firstCell = r.nextInt(numOfCells);
		g = makeGraph(maze);
		visited = new boolean[numOfCells];
		for (int i = 0; i < visited.length; i++) { //init loop
			visited[i] = false;
		}

		runDFS();
		carveMaze(maze);
	}

	//Function that deletes a wall between 2 given cells:
	private void deleteWall(Cell c1, Cell c2) {
		//Loop thru 4 sides:
		for(int s = 0; s < sides.length; ++s) {
			if(c1.neigh[sides[s]] == c2) {
				c1.wall[sides[s]].present = false;
				c2.wall[Maze.oppoDir[sides[s]]].present = false;
				break;
			}
		}
	}

	//Makes the maze based on the DFS result:
	private void carveMaze(Maze maze) {
		Cell c = cells.get(firstCell);
		for(int i : DFSList) {
			deleteWall(c, cells.get(i));
			c = cells.get(i);
		}
	}

	//Maps all maze cells to graph as integers, and links neighboring cells:
	private Graph makeGraph(Maze maze) {
		Graph g = new Graph(numOfCells);

		for(Cell[] array : maze.map) {
			for(Cell c : array) {
				if(c != null) {cells.add(c);}
			}
		}

		System.out.println("NUMCELLS: " + cells.size());

		//Loops thru cells, adds edges to graph based on cell neighbors:
		for(Cell c : cells) {
			//Loop thru 4 sides:
			for(int s = 0; s < sides.length; ++s) {
				if(c.neigh[sides[s]] != null) {
					g.addEdge(cells.indexOf(c), cells.indexOf(c.neigh[sides[s]]));
				}
			}
			//For tunnels:
			if(c.tunnelTo != null) {
				g.addEdge(cells.indexOf(c), cells.indexOf(c.tunnelTo));
			}
		}
		return g;
	}

	//Run the depth first search traversal upon the graph:
	private void runDFS() {
		//Add first cell, mark as visited:
		DFSList.add(firstCell);
		visited[firstCell] = true;
		//Get neighbors:
		int n[] = g.neighbours(firstCell);

		//Main DFS loop:
		for(int i : n) {
			if(!visited[i]) {dfs(i);}
		}
	}

	//Recursive DFS method:
	private void dfs(int i) {
		//Add cell to list, mark as visited:
		DFSList.add(i);
		visited[i] = true;

		if(cells.get(i).tunnelTo != null) {
			int j = cells.indexOf(cells.get(i).tunnelTo);
			if(!visited[j]) {dfs(j);}
		}
		//Get neighbors:
		int n[] = g.neighbours(i);

		for (int j : n) {
			if(!visited[j]) {dfs(j);}
			if(DFSList.get(DFSList.size() - 1) != i) {DFSList.add(i);}
		}
	}

}//END OF FILE
