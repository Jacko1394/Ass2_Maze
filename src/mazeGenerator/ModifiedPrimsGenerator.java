package mazeGenerator;

import graph.*;
import maze.*;

import java.lang.reflect.Array;
import java.util.*;


public class ModifiedPrimsGenerator implements MazeGenerator {

	//Variables:
	Random r = new Random();  //Random number generator
	int numOfCells = 0;  //Number of cells in the maze
	int firstCell = 0;  //Randomly selected starting cell for DFS
	ArrayList<Cell> cells = new ArrayList<>();  //Arraylist of the maze cells
	Graph g = null;  //Graph structure to represent maze cell verticies
	boolean[] visited; //Array of marked cells
	//Constants:
	final static int[] sides = {Maze.NORTH, Maze.SOUTH, Maze.EAST, Maze.WEST};
	//set for frontier
	//private ArrayList<Cell> allCell;
	//private ArrayList<Cell> Z = new ArrayList<>();
	private ArrayList<Cell> F;
	//
	private ArrayList<Edge> allCell =new ArrayList<>();
    //private ArrayList<Edge> Z =new ArrayList<>();
    //private ArrayList<Edge> F;

    public ModifiedPrimsGenerator() {
    }


    //set for Z

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
		//g.sortWeight();
		System.out.println("Running ModPrims...");
		runModPrims();
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

	//Run the depth first search traversal upon the graph:
	private void runModPrims() {
        HashMap<Integer,Set<Integer>> Z = new HashMap<>();
        for(int i=0; i<numOfCells; i++)
        {
            //Each set stores the known vertices reachable from this vertex
            //initialize with it self.
            Set<Integer> vs = new HashSet<>();
            vs.add(i);
            Z.put(i, vs);
        }
        allCell.addAll(g.mEdge);
        Collections.shuffle(allCell);
        for ( Edge e : allCell) {
            int homeCell = e.getV1();
            int nextCell = e.getV2();
            Set<Integer> visited1 = Z.get(homeCell);
            Set<Integer> visited2 = Z.get(nextCell);
            if(visited1.equals(visited2))
                continue;
            visited1.addAll(visited2);
            for(Integer i : visited1)
            {
                Z.put(i, visited1);
            }
            deleteWall(cells.get(homeCell), cells.get(nextCell));
            System.out.println(homeCell);
        }



/*		weightCell.addAll(g.mEdge);
 		while (!weightCell.isEmpty()){
            int randCell = r.nextInt(weightCell.size());
            Edge e = weightCell.get(randCell);
            Z.add(weightCell.remove(randCell));
            int home = e.getV1();
            int next = getneighbour(home);
            deleteWall(cells.get(home), cells.get(next));
        }*/

        /*
		for (Edge e: g.mEdge) {

		}

		allCell = new ArrayList<>(cells);
		while (!allCell.isEmpty()){
			int start = r.nextInt(allCell.size());
			Cell homeCell = allCell.get(start);
			Z.add(homeCell);
			allCell.remove(homeCell);
			F = new ArrayList<>();
			for (Cell c:homeCell.neigh) {
				if(c != null & !Z.contains(c)){
					F.add(c);
				}
			}
			if (!F.isEmpty()) {
				int randFrontier = r.nextInt(F.size());
				deleteWall(homeCell, F.get(randFrontier));
			}

		}*/
	}

	private int getneighbour(int home) {
		ArrayList<Edge> neighbour = new ArrayList();
		for (Edge e: g.mEdge) {
			if(e.getV1() == home){
                neighbour.add(e);
            }
            if(e.getV2() == home){
                neighbour.add(e);
            }
		}
        Collections.sort(neighbour);
        //int rand = r.nextInt(neighbour.size());
        if(neighbour.get(0).getV2()==home)
            return neighbour.get(0).getV1();
        else
            return neighbour.get(0).getV2();
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

} // end of class ModifiedPrimsGenerator
