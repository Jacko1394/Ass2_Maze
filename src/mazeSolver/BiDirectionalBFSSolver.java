package mazeSolver;

import maze.*;
import java.util.*;

//Implements Bi-directional BFS maze solving algorithm:
public class BiDirectionalBFSSolver implements MazeSolver {

	Queue<Cell> leftQueue = new LinkedList<>();
	Queue<Cell> rightQueue = new LinkedList<>();
	ArrayList<Cell> leftVisited = new ArrayList<>();
	ArrayList<Cell> rightVisited = new ArrayList<>();
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

		//Add end points to start of each queue:
		leftQueue.add(maze.entrance);
		rightQueue.add(maze.exit);

		while(!found) {
			//LEFT SIDE::::::::::::::::
			//Checks if paths have met:
			maze.drawFtPrt(leftQueue.peek());
			if(!rightVisited.contains(leftQueue.peek())) { //NOT MET
				ArrayList<Cell> n = getNeighbors(leftQueue.peek());

				for(Cell c : n) {
					if(!leftVisited.contains(c)) {
						leftQueue.add(c);
					}
				}

				leftVisited.add(leftQueue.remove());

			} else {
				found = true;
				break;
			}

			//RIGHT SIDE::::::::::::::::
			//Checks if paths have met:
			maze.drawFtPrt(rightQueue.peek());
			if(!leftVisited.contains(rightQueue.peek())) { //NOT MET
				ArrayList<Cell> n = getNeighbors(rightQueue.peek());

				for(Cell c : n) {
					if(!rightVisited.contains(c)) {
						rightQueue.add(c);
					}
				}

				rightVisited.add(rightQueue.remove());

			} else {
				found = true;
				break;
			}
			//Add 2 to cell count (left side + right side):
			cellCount += 2;
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
		return cellCount;
	}

}//END OF FILE
