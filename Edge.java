public class Edge {
    public int Weight;
    public String Terget_name;
    public Vertex Target;

    public Edge(int W, Vertex T, String N){
        Weight = W;
        Target = T;
        Terget_name = N;
    }
}
