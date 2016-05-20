package graph;

//Created by nur on 18/05/16

class Edge implements Comparable<Edge> {
    int wt, v1, v2;

    Edge(int wt, int v1, int v2)
    {
        this.wt=wt;
        this.v1=v1;
        this.v2=v2;
    }

    @Override
    public int compareTo(Edge o) {
        Edge e1 = (Edge)o;
        if(e1.wt==this.wt)
            return 0;
        return e1.wt < this.wt ? 1 : -1;
    }

    @Override
    public String toString()
    {
        return String.format("Vertex1:%d \t Vertex2:%d \t Cost:%d\n", v1,v2,wt);

    }
}//END OF FILE
