import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public String Name;
    public List<Edge> Edges = new ArrayList<Edge>();
    
    public void add_Edge(Edge E){
        Edges.add(E);
    }

    public Vertex(String N){
        Name = N;
    }
}
