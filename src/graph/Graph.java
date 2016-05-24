package graph;
import java.util.*;

public class Graph {
	//Number of vertices (nodes) in the graph:
    private int mVertNum;
    //Adjacency list representation for graph:
    private ArrayList<Set<Integer>> mAdj;
    //speeding up adding weight to edge
    private HashSet<Integer> unique = new HashSet<>();
    //private HashSet<Integer> VW = new HashSet<>();
    //Random number generator
    Random r = new Random();
    public Set<Edge> mEdge = new HashSet<Edge>();
    //private Set<Edge> sortedList = new HashSet<Edge>();
    public ArrayList<Edge> sortedArrayList;

    //Initializes an empty graph with vertices and 0 edges:
    public Graph(int vertNum) throws IllegalArgumentException {
        if (vertNum < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        generateAdjList(vertNum);
    }

	//Generates the adjacency list:
    private void generateAdjList(int vertNum) {
        mVertNum = vertNum;
        mAdj = new ArrayList<>(vertNum);
        for (int v = 0; v < vertNum; v++) {
            mAdj.add(new TreeSet<>());
        }
    }

    //Returns the number of vertices in this graph:
    public int vertNum() {return mVertNum;}

    //Checks vertex is valid:
    private void validateVertex(int v) {
        if (v < 0 || v >= mVertNum)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (mVertNum-1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
		mAdj.get(v).add(w);
		mAdj.get(w).add(v);
    }

    //Adds the undirected edge v-w to this graph:
    public void addWeightedEdge(int v, int w) {
        mAdj.get(v).add(w);
        mAdj.get(w).add(v);
        //System.out.println((v*w)+2*(v+w));
        int rand = r.nextInt(mVertNum) + 1;
        Edge e = new Edge(v, w, rand);
        //(v*w)+2*(v+w) ensures uniqueness of edges
        if (!(unique.contains((v*w)+2*(v+w)))){
            unique.add((v*w)+2*(v+w));
            mEdge.add(e);
        }
    }

    public void sortWeight(){
        sortedArrayList = new ArrayList(mEdge);
        Collections.sort(sortedArrayList);
        System.out.println();
    }

    //Returns the vertices adjacent to vertex in RANDOM ORDER:
    public int[] neighbours(int i) {
        validateVertex(i);
		//Put neighbors in arraylist and randomize:
        ArrayList<Integer> n = new ArrayList<>();
		n.addAll(mAdj.get(i));
		Collections.shuffle(n);

		//Put into primitive array:
		int[] array = new int[n.size()];
		for(int x = 0; x < array.length; ++x) {array[x] = n.get(x);}
		return array;
	}

	//Returns the degree of vertex:
    public int degree(int v) {
        validateVertex(v);
        return mAdj.get(v).size();
    }

    //Returns a string representation of this graph:
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(mVertNum + " vertices\n");
        for (int v = 0; v < mVertNum; v++) {
            s.append(v + ": ");
            for (int w : mAdj.get(v)) {s.append(w + " ");}
            s.append("\n");
        }
        return s.toString();
    }
}//END OF FILE