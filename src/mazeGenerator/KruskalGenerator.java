package mazeGenerator;

import graph.Graph;
import maze.*;
import java.util.*;

public class KruskalGenerator implements MazeGenerator {

	//Variables:
	Random r = new Random();  //Random number generator
	int numOfCells = 0;  //Number of cells in the maze
	int firstCell = 0;  //Randomly selected starting cell for DFS
	ArrayList<Cell> cells = new ArrayList<>();  //Arraylist of the maze cells
	ArrayList<Integer> DFSList = new ArrayList<>();  //Array list of DFS order
	Graph g = null;  //Graph structure to represent maze cell verticies
	boolean[] visited= {false, false, false, false, false};// = null;  //Array of marked cells

	//Constants:
	final static int[] sides = {Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST};

	@Override
	public void generateMaze(Maze maze) {
		//Initialization of class values:
		numOfCells = maze.sizeR * maze.sizeC;
		firstCell = r.nextInt(numOfCells);
		g = makeGraph(maze);
		visited = new boolean[numOfCells];

		for (int i = 0; i < visited.length; i++) { //init loop
			visited[i] = false;
		}
		g.sortWeight();
		System.out.println("Running Kruskal...");
		runKRSKL();
		carveMaze(maze);
	}

	//Run the depth first search traversal upon the graph:
	private void runKRSKL() {
		//make a forest of empty tree with vertex itsefl
		HashMap<Integer,Set<Integer>> forest = new HashMap<Integer,Set<Integer>>();
		for(int i=0; i<numOfCells; i++)
		{
			//Each set stores the known vertices reachable from this vertex
			//initialize with it self.
			Set<Integer> vs = new HashSet<Integer>();
			vs.add(i);
			forest.put(i, vs);
		}

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
		int cellNum = 0;

		//Loops thru each cell in the maze:
		for(int r = 0; r < maze.sizeR; ++r) { //Next row
			for(int c = 0; c < maze.sizeC; ++c) { //Next coloumb
				cells.add(cellNum, maze.map[r][c]);
				++cellNum;
			}
		}
		//Loops thru cells, adds edges to graph based on cell neighbors:
		for(Cell c : cells) {
			//Loop thru 4 sides:
			for(int s = 0; s < sides.length; ++s) {
				if(c.neigh[sides[s]] != null) {
					g.addEdge(cells.indexOf(c), cells.indexOf(c.neigh[sides[s]]));
				}
			}
		}
		return g;
	}



	//Recursive DFS method:
	private void dfs(int i) {
		//Add cell to list, mark as visited:
		DFSList.add(i);
		visited[i] = true;
		//Get neighbors:
		int n[] = g.neighbours(i);

		for (int j : n) {
			if(!visited[j]) {dfs(j);}
			if(DFSList.get(DFSList.size() - 1) != i) {DFSList.add(i);}
		}
	}

} // end of class KruskalGenerator