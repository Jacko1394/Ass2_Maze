package mazeGenerator;

import java.util.*;
import maze.*;
import graph.*;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	//Variables:
	Random r = new Random();  //Random number generator

	int[] sides;  //Array of the cell side int consts, (diff for normal/hex)
	int numOfCells = 0;  //Number of cells in the maze
	int firstCell = 0;  //Randomly selected starting cell for DFS
	boolean[] visited;  //Array of marked cells

	Graph g = null;  //Graph structure to represent maze cell verticies
	ArrayList<Cell> cells = new ArrayList<>();  //Arraylist of the maze cells
	Stack<Cell> cellStack = new Stack<>(); //stack dfs for big big mazes xD

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

		runStackDFS();
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
					g.addEdge(cells.indexOf(c), cells.indexOf(c.neigh[s]));
				}
			}
			//For tunnels:
			if(c.tunnelTo != null) {
				g.addEdge(cells.indexOf(c), cells.indexOf(c.tunnelTo));
			}
		}
		return g;
	}

	//Runs the stack based implementation of DFS algorithm:
	private void runStackDFS() {
		//Variables:
		int cellCount = 1;
		boolean neighbors;
		//Add first cell (random) to stack, set as visited:
		cellStack.push(cells.get(firstCell));
		visited[cells.indexOf(cellStack.peek())] = true;

		//Loop until all cells are accounted for:
		while(cellCount < numOfCells) {
			//Get neighbors of top of stack cell:
			int n[] = g.neighbours(cells.indexOf(cellStack.peek()));
			neighbors = false;  //Reset to false

			//Check for tunnel:
			if(cellStack.peek().tunnelTo != null) {
				//Check if tunnel cell has been visited:
				int t = cells.indexOf(cellStack.peek().tunnelTo);
				if(!visited[t]) {
					cellStack.push(cells.get(t));
					visited[t] = true;
					++cellCount;
					continue;
				}
			}

			//If no tunnel, loop thru neighbor cells:
			for(int i : n) {
				if(!visited[i]) {
					deleteWall(cellStack.peek(), cells.get(i));
					cellStack.push(cells.get(i));
					visited[i] = true;
					++cellCount;
					neighbors = true;
					break;
				}
			}
			//If all neighbors have been visited, pop that cell off the stack:
			if(!neighbors) {
				cellStack.pop();}
		}
	}

}//END OF FILE
