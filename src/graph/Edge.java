package graph;

/**
 * Created by nur on 18/05/16.
 */
public class Edge implements Comparable<Edge>
{
    int v1, v2, wt;

    Edge(int v1, int v2, int wt)
    {
        this.v1=v1;
        this.v2=v2;
        this.wt=wt;
    }

    public int getV1(){
        return v1;
    }

    public int getV2(){
        return v2;
    }

    public int getWt(){
        return wt;
    }

    @Override
    public int compareTo(Edge o) {
        Edge e1 = (Edge)o;
        if(e1.wt==this.wt)
            return 0;
        return e1.wt < this.wt ? 1 : -1;
    }
}