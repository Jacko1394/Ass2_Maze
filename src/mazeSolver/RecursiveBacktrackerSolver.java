package mazeSolver;

import maze.*;
import java.util.*;

public class RecursiveBacktrackerSolver implements MazeSolver {

	//Variables:
	Maze maze = null;
	ArrayList<Cell> visited = new ArrayList<>();
	boolean found = false;
	int cellCount = 0;
	//int solvedPathCount = 0;

	//Constants:
	static int[] sides;

	@Override
	public void solveMaze(Maze maze) {
		if(maze.type == Maze.HEX) {  //Hex sides:
			sides = new int[] {Maze.NORTHEAST, Maze.SOUTHEAST, Maze.NORTHWEST, Maze.SOUTHWEST, Maze.EAST, Maze.WEST};
		} else {  //Normal sides:
			sides = new int[] {Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST};
		}
		//Init:
		this.maze = maze;
		++cellCount;
		//Start at maze entrance:
		visited.add(maze.entrance);
		maze.drawFtPrt(maze.entrance);

		//Arraylist of RANDOM neighboring cells:
		ArrayList<Cell> n = getNeighbors(maze.entrance);
		for(Cell c : n) {
			if(!visited.contains(c)) {
				if(dfs(c)) {break;}
			}
		}
	}

	//Returns an arraylist of neighboring cells that can be accessed:
	//In RANDOM order:
	private ArrayList<Cell> getNeighbors(Cell c) {
		ArrayList<Cell> n = new ArrayList<>();
		for(int i = 0; i < sides.length; ++i) {
			if(!c.wall[sides[i]].present) {
				n.add(c.neigh[sides[i]]);
			}
		}

		if(c.tunnelTo != null) {
			n.add(c.tunnelTo);
		}

		Collections.shuffle(n);
		return n;
	}

	//Recursive DFS method, that implements maze visualisation:
	private boolean dfs(Cell c) {
		maze.drawFtPrt(c);
		//Check if exit found:
		if(c == maze.exit) {
			found = true;
			return true;
		}
		//Continue recursion:
		++cellCount;
		visited.add(c);
		ArrayList<Cell> n = getNeighbors(c);
		for(Cell next : n) {
			if(!visited.contains(next)) {
				if(dfs(next)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isSolved() {return found;}
	@Override
	public int cellsExplored() {return cellCount;}

}//END OF FILE
