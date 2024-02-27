import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Prims {
  public static void main(String[] args){
    /*
     * objects structures:
     * Vertex has a list of edges
     * Edge has a int Weight, Vertex Target
     * a Tree is a dictionary of vertices
     * 
     * Examples:
     * Edge = [int 12, Vertex A]
     * Vertex = [Edge ed, Edge ed, Edge ed, Edge ed]
     * Tree = {A: Vertex A, B: Vertex B, C: Vertex C, D: Vertex D}
     */
    LoadGraph();
    Prim();
  }

  // set global variables
  private static HashMap<String, Vertex> Vertices = new HashMap<>();
  private static HashMap<String, Vertex> MST = new HashMap<>();
  
  private static void LoadGraph() {
    /*
     * fill the list of vertices
     */
    // read data from file and cunstruct the vertices
    try{
      File myObj = new File("Graph.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        // get the left and right vertices
        String data = myReader.nextLine();
        String[] splitted_data1 = data.split(",");
        String[] splitted_data2 = splitted_data1[1].split(" - ");
        String vertex = splitted_data1[0];
        String target = splitted_data2[0];
        String weight = splitted_data2[1];
        int WEIGTH = Integer.valueOf(weight);

        // construct 2 new vertices
        Vertex VERTEX = new Vertex(vertex);
        Vertex TARGET = new Vertex(target);

        // construct vertex and target
        Edge VERTEX_EDGE = new Edge(WEIGTH, TARGET, target);
        VERTEX.add_Edge(VERTEX_EDGE);

        Edge TARGET_EDGE = new Edge(WEIGTH, VERTEX, vertex);
        TARGET.add_Edge(TARGET_EDGE);

        // add vertex to the list
        if (!Vertices.containsKey(vertex)){
          Vertices.put(vertex, VERTEX);
          //System.out.println("Added new Vertex: "+Left);
        }else{
          Vertex v = Vertices.get(vertex);
          v.add_Edge(VERTEX_EDGE);
        }
        // add RIGHT vertecies to the list
        if (!Vertices.containsKey(target)){
          Vertices.put(target, TARGET);
          //System.out.println("Added new Vertex: "+Right);
        }
      } 
      myReader.close();
    }catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private static void Prim() {
    /*
     * go over each vertex in the list of vertices
     * go over each edge in the vertex and select the lowest weight
     * if the selected vertex OR the target vertex are not in the MST
     *  add the selected vertex to the MST
     *  add the target vertex to the MST
     *  to the selected vertex add a new edge with the weight and target vertex
     *  to the target vertex add a new edge with the weight and selected vertex
    */
    
    // go over each vertex in the list of vertices
    double lowest_weight = Double.POSITIVE_INFINITY;
    boolean add_edge = false;
    Edge selected_edge = null;
    Edge tmp_selected_edge = null;
    Vertex MST_target = null;
    Vertex MST_vertex = null;
    int low_w = 0;
    List<String> used_edges = new ArrayList<String>();

    for (Map.Entry<String, Vertex> entry : Vertices.entrySet()){
      String name = entry.getKey();
      Vertex vert = entry.getValue();
      System.out.println("\nSelect Vertex: "+vert.Name);
      
      // go over each edge in the vertex and select the lowest weight
      lowest_weight = Double.POSITIVE_INFINITY;
      selected_edge = null;
      tmp_selected_edge = null;
      MST_target = null;
      MST_vertex = null;
      low_w = 0;
      add_edge = false;
      for (Edge Edg : vert.Edges){
        System.out.println("Select edge: ("+name+Edg.Terget_name+", w = "+Integer.toString(Edg.Weight)+")");
        //System.out.println(Edg.Weight);
        //System.out.println(lowest_weight);
        if (Edg.Weight < lowest_weight){
          tmp_selected_edge = Edg;
          if (!used_edges.contains(tmp_selected_edge.Terget_name+name) && !used_edges.contains(name+tmp_selected_edge.Terget_name)){
            selected_edge = Edg;
            lowest_weight = Edg.Weight;
            low_w = (int)lowest_weight;
            System.out.println("Selected new lowest weight: "+Integer.toString(low_w));
            add_edge = true;
          }
        }

        // if the selected vertex OR the target vertex are not in the MST
        if (!MST.containsKey(name)){
          MST_vertex = new Vertex(name);
          MST.put(name, MST_vertex);
          System.out.println("Adding vertex: "+name+" To MST");
        }
      
        if (!MST.containsKey(Edg.Terget_name)){
          MST_target = new Vertex(Edg.Terget_name);
          MST.put(Edg.Terget_name, MST_target);
          System.out.println("Adding vertex: "+Edg.Terget_name+" To MST");
        }
      }

      // add selected edge to MST
      //System.out.println(add_edge);
      if (add_edge && selected_edge != null){
        Vertex select_t = MST.get(selected_edge.Terget_name);
        Edge MST_Edge_for_target = new Edge(low_w, MST_vertex, name);
        select_t.add_Edge(MST_Edge_for_target);
        System.out.println("Add edge: ("+name+selected_edge.Terget_name+", w = "+Integer.toString(selected_edge.Weight)+") to "+selected_edge.Terget_name);
        used_edges.add(name+selected_edge.Terget_name);

        // add the selected edge to the new MST vertex and target vertex
        Vertex select_v = MST.get(name);
        Edge MST_Edge_for_vertex = new Edge(low_w, MST_target, selected_edge.Terget_name);
        select_v.add_Edge(MST_Edge_for_vertex);
        System.out.println("Add edge: ("+selected_edge.Terget_name+name+", w = "+Integer.toString(selected_edge.Weight)+") to "+name);
        used_edges.add(selected_edge.Terget_name+name);
      }

      
    }
    System.out.println("\n\nMST");
    for (Map.Entry<String, Vertex> entry : MST.entrySet()){
      String name = entry.getKey();
      Vertex vert = entry.getValue();
      System.out.println("\nVertex: "+vert.Name);
      for (Edge Edg : vert.Edges){
        System.out.println("Edge: ("+name+Edg.Terget_name+", w = "+Integer.toString(Edg.Weight)+")"); 
      }
    }
  }
}