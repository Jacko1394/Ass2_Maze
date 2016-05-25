package mazeGenerator;

import graph.*;
import maze.*;
import java.util.*;

public class KruskalGenerator implements MazeGenerator {

	//Variables:
	Random r = new Random();  //Random number generator
	int numOfCells = 0;  //Number of cells in the maze
	int firstCell = 0;  //Randomly selected starting cell for DFS
	ArrayList<Cell> cells = new ArrayList<>();  //Arraylist of the maze cells
	Graph g = null;  //Graph structure to represent maze cell verticies
	boolean[] visited; //Array of marked cells
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
	}

	//Maps all maze cells to graph as integers, and links neighboring cells:
	private Graph makeGraph(Maze maze) {
		Graph g = new Graph(numOfCells);

		for(Cell[] array : maze.map) {
			for(Cell c : array) {
				if(c != null) {cells.add(c);}
			}
		}

		//Loops thru cells, adds edges to graph based on cell neighbors:
		for(Cell c : cells) {
			//Loop thru 4 sides:
			for(int s : sides) {
				if(c.neigh[s] != null) {
					g.addWeightedEdge(cells.indexOf(c), cells.indexOf(c.neigh[s]));
				}
			}
			//For tunnels:
			if(c.tunnelTo != null) {
				g.addWeightedEdge(cells.indexOf(c), cells.indexOf(c.tunnelTo));
			}
		}
		return g;
	}

	//this will generate the maze based on the ascending weighted edge.
	private void runKRSKL() {
		HashMap<Integer,Set<Integer>> forest = new HashMap<>();
		for(int i=0; i<numOfCells; i++)
		{
			//Each set stores the known vertices reachable from this vertex
			//initialize with it self.
			Set<Integer> vs = new HashSet<>();
			vs.add(i);
			forest.put(i, vs);
		}

		for ( Edge e : g.sortedArrayList) {
			int homeCell = e.getV1();
			int nextCell = e.getV2();
			Set<Integer> visited1 = forest.get(homeCell);
			Set<Integer> visited2 = forest.get(nextCell);
			if(visited1.equals(visited2))
				continue;
			visited1.addAll(visited2);
			for(Integer i : visited1)
			{
				forest.put(i, visited1);
			}
			deleteWall(cells.get(homeCell), cells.get(nextCell));
			System.out.println(homeCell);
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

} // end of class KruskalGenerator