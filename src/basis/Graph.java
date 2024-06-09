package basis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: basis.Graph
 * Package: PACKAGE_NAME
 * Description: This class represents a graph data structure used to store vertices and edges.
 *
 * @version 1.0
 * @date: 2024/5/13 16:48
 */
public class Graph {
  /**
   * Stores the vertices in the graph.
   */
  private ArrayList<Vertex> vertices;

  /**
   * Constructs a graph with the given graph.
   *
   * @param graph the graph to copy.
   */
  public Graph(Graph graph) {
    this.vertices = new ArrayList<>(copyVertices(graph.getVertices()));
  }

  /**
   * Constructs an empty graph.
   */
  public Graph() {
    this.vertices = new ArrayList<>();
  }

  /**
   * Returns a deep copy of all vertices in the graph.
   *
   * @return an ArrayList containing copies of all vertices in the graph.
   */
  public ArrayList<Vertex> getVertices() {
    return new ArrayList<>(copyVertices(vertices));
  }

  /**
   * Sets all vertices in the graph with copies of the given vertices.
   *
   * @param vertices an ArrayList containing vertices to set in the graph.
   */
  public void setVertices(ArrayList<Vertex> vertices) {
    this.vertices = new ArrayList<>(copyVertices(vertices));
  }

  /**
   * Adds a vertex to the graph.
   *
   * @param name the name of the vertex to add.
   */
  public void addVertex(String name) {
    for (Vertex v : vertices) {
      if (v.getName().equalsIgnoreCase(name)) {
        return;
      }
    }
    vertices.add(new Vertex(name.toLowerCase()));
  }

  /**
   * Adds an edge to the graph.
   *
   * @param preName the name of the starting vertex of the edge.
   * @param curName the name of the ending vertex of the edge.
   */
  public void addEdge(String preName, String curName) {
    Vertex pre = null;
    Vertex cur = null;
    for (Vertex v : vertices) {
      if (v.getName().equalsIgnoreCase(preName)) {
        pre = v;
      }
      if (v.getName().equalsIgnoreCase(curName)) {
        cur = v;
      }
    }
    if (pre != null && cur != null) {
      if (pre.getNextvSet().contains(cur)) {
        int weight = pre.getWeight().get(cur);
        pre.getWeight().replace(cur, 1 + weight);
      } else {
        HashSet<Vertex> nextvSet = pre.getNextvSet();
        nextvSet.add(cur);
        pre.setnextvSet(nextvSet);
        HashSet<Vertex> prevSet = cur.getPrevSet();
        prevSet.add(pre);
        cur.setPrevSet(prevSet);
        pre.getWeight().put(cur, 1);
      }
    }
  }

  /**
   * Returns a string representation of the graph.
   *
   * @return a string representation of the graph.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Vertex v : vertices) {
      result.append(v.toString()).append(": ").append(v.getNextvSet().toString()).append("\n");
    }
    return result.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(vertices);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Graph graph = (Graph) o;
    return Objects.equals(vertices, graph.vertices);
  }

  /**
   * Creates a deep copy of the given list of vertices.
   *
   * @param vertices the list of vertices to copy.
   * @return a deep copy of the list of vertices.
   */
  private ArrayList<Vertex> copyVertices(List<Vertex> vertices) {
    ArrayList<Vertex> copy = new ArrayList<>();
    for (Vertex v : vertices) {
      copy.add(new Vertex(v));
    }
    return copy;
  }
}
