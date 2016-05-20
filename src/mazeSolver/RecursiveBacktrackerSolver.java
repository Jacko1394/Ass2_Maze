package mazeSolver;

import maze.*;
import java.util.*;

public class RecursiveBacktrackerSolver implements MazeSolver {

	//Variables:
	ArrayList<Cell> visited = new ArrayList<>();
	Stack<Cell> cellStack = new Stack<>();
	boolean found = false;
	int cellCount = 1;
	int[] sides;

	@Override
	public void solveMaze(Maze maze) {
		if(maze.type == Maze.HEX) {  //Hex sides:
			sides = new int[] {Maze.NORTHEAST, Maze.SOUTHEAST, Maze.NORTHWEST, Maze.SOUTHWEST, Maze.EAST, Maze.WEST};
		} else {  //Normal sides:
			sides = new int[] {Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST};
		}

		boolean neighbors;
		cellStack.push(maze.entrance);
		visited.add(maze.entrance);

		while(!found) {
			//Reset neighbors bool:
			neighbors = false;
			//Check if exit is found (solved):
			if(cellStack.peek() == maze.exit) {
				found = true;
				break;
			}

			maze.drawFtPrt(cellStack.peek());
			ArrayList<Cell> n = getNeighbors(cellStack.peek());

			for(Cell c : n) {
				if(!visited.contains(c)) {
					++cellCount;
					cellStack.push(c);
					visited.add(c);
					neighbors = true;
					break;
				}
			}
			//If a dead end is reached, pop the stack:
			if(!neighbors) {
				cellStack.pop();
			}
		}
	}

	//Returns an arraylist of neighboring cells that can be accessed:
	private ArrayList<Cell> getNeighbors(Cell c) {
		ArrayList<Cell> n = new ArrayList<>();
		for(int i : sides) {
			if(!c.wall[i].present) {
				n.add(c.neigh[i]);
			}
		}

		if(c.tunnelTo != null) {
			n.add(c.tunnelTo);
		}
		//Return in random order:
		Collections.shuffle(n);
		return n;
	}

	@Override
	public boolean isSolved() {return found;}
	@Override
	public int cellsExplored() {
		System.out.println("Solve path (num. cells) = " + cellStack.capacity());
		return cellCount;
	}

}//END OF FILE
