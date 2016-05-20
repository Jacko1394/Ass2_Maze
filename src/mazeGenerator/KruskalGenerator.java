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
	ArrayList<Integer> DFSList = new ArrayList<>();  //Array list of DFS order
	Graph g = null;  //Graph structure to represent maze cell verticies
	boolean[] visited= {false, false, false, false, false};// = null;  //Array of marked cells
	ArrayList minSpanTree = new ArrayList<>();
	//ArrayList<Edge> newSort = new ArrayList<>();
	//private Set<Object> sortedList = new HashSet<>();
	//Edge check;

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
		//carveMaze(maze);
	}

	//Run the depth first search traversal upon the graph:
	private void runKRSKL() {
		System.out.println();
		deleteWall(cells.get(0), cells.get(1));
		//deleteWall(cells.get(firstCell));

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


	//make a forest of empty tree with vertex itsefl
/*		HashMap<Integer,Set<Integer>> forest = new HashMap<Integer,Set<Integer>>();
		for(int i=0; i<numOfCells; i++)
		{
			//Each set stores the known vertices reachable from this vertex
			//initialize with it self.
			Set<Integer> vs = new HashSet<Integer>();
			vs.add(i);
			forest.put(i, vs);
		}*/

/*		for (int i=0; i<g.sortedArrayList.size(); i++) {
			newSort.add((Edge) g.sortedArrayList.get(i));
		}*/

/*		while(true) //while you haven't visited all the vertices at least once
		{
			//boolean add = sortedList.add(g.sortedArrayList.get(0));
			//check.add((Edge) g.sortedArrayList.get(0));
            	//sortedList.add(g.mEdge.remove());
        	//}

			System.out.println();
			check = newSort.remove(0);//pop

			Set<Integer> visited1 = forest.get(check.);
			Set<Integer> visited2 = forest.get(check);
			if(visited1.equals(visited2))
				continue;
			minSpanTree.add(check);
			visited1.addAll(visited2);
			for(Integer i : visited1)
			{
				forest.put(i, visited1);
			}
			//if(visited1.size()==vertices.length)
				break;
		}*/
/*	//Function that deletes a wall between 2 given cells:
	private void deleteWall(Cell c1, Cell c2) {
		//Loop thru 4 sides:
		for(int s = 0; s < sides.length; ++s) {
			if(c1.neigh[sides[s]] == c2) {
				c1.wall[sides[s]].present = false;
				c2.wall[Maze.oppoDir[sides[s]]].present = false;
				break;
			}
		}
	}*/

/*	//Makes the maze based on the DFS result:
	private void carveMaze(Maze maze) {
		Cell c = cells.get(firstCell);
		for(int i : DFSList) {
			deleteWall(c, cells.get(i));
			c = cells.get(i);
		}
	}*/

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

} // end of class KruskalGenerator