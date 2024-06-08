package basis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a vertex in a graph.
 * Each vertex has a name and sets of predecessor and successor vertices.
 * Weights of edges to successor vertices are stored in a map.
 *
 * @date: 2024/5/13 16:38
 */
public class Vertex {
  private String name;
  private HashSet<Vertex> prevSet;
  private HashSet<Vertex> nextvSet;
  public HashMap<Vertex, Integer> weight;

  /**
   * Constructs an empty Vertex.
   * The name is set to null, and the predecessor and successor sets
   * and weight map are initialized to empty sets
   * and map.
   */
  public Vertex() {
    this.name = null;
    this.prevSet = new HashSet<>();
    this.nextvSet = new HashSet<>();
    this.weight = new HashMap<>();
  }

  /**
   * Constructs a Vertex with the specified name.
   * The predecessor and successor sets and weight map are initialized to empty sets and map.
   *
   * @param name the name of the vertex
   */
  public Vertex(String name) {
    this.name = name;
    this.prevSet = new HashSet<>();
    this.nextvSet = new HashSet<>();
    this.weight = new HashMap<>();
  }

  /**
   * Constructs a Vertex with the specified name, predecessor set, successor set, and weight map.
   *
   * @param name     the name of the vertex
   * @param prevSet  the set of predecessor vertices
   * @param nextvSet the set of successor vertices
   * @param weight   the map of weights to successor vertices
   */
  public Vertex(String name, HashSet<Vertex> prevSet,
                HashSet<Vertex> nextvSet, HashMap<Vertex, Integer> weight) {
    this.name = name;
    this.prevSet = prevSet;
    this.nextvSet = nextvSet;
    this.weight = weight;
  }

  /**
   * Returns the set of predecessor vertices.
   *
   * @return the set of predecessor vertices
   */
  public HashSet<Vertex> getPrevSet() {
    return prevSet;
  }

  /**
   * Returns the name of the vertex.
   *
   * @return the name of the vertex
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the vertex.
   *
   * @param name the new name of the vertex
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the set of predecessor vertices.
   *
   * @return the set of predecessor vertices
   */
  public HashSet<Vertex> nextvSet() {
    return prevSet;
  }

  /**
   * Sets the set of predecessor vertices.
   *
   * @param prevSet the new set of predecessor vertices
   */
  public void setPrevSet(HashSet<Vertex> prevSet) {
    this.prevSet = prevSet;
  }

  /**
   * Returns the set of successor vertices.
   *
   * @return the set of successor vertices
   */
  public HashSet<Vertex> getNextvSet() {
    return nextvSet;
  }

  /**
   * Sets the set of successor vertices.
   *
   * @param nextvSet the new set of successor vertices
   */
  public void setnextvSet(HashSet<Vertex> nextvSet) {
    this.nextvSet = nextvSet;
  }

  /**
   * Returns the map of weights to successor vertices.
   *
   * @return the map of weights to successor vertices
   */
  public HashMap<Vertex, Integer> getWeight() {
    return weight;
  }

  /**
   * Sets the map of weights to successor vertices.
   *
   * @param weight the new map of weights to successor vertices
   */
  public void setWeight(HashMap<Vertex, Integer> weight) {
    this.weight = weight;
  }

  /**
   * Checks if this vertex is equal to another object.
   * Two vertices are considered equal if they have the same name,
   * predecessor set, successor set, and weight map.
   *
   * @param o the object to compare with
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vertex vertex = (Vertex) o;
    return Objects.equals(name, vertex.name)
        && Objects.equals(prevSet, vertex.prevSet)
        && Objects.equals(nextvSet, vertex.nextvSet)
        && Objects.equals(weight, vertex.weight);
  }

  /**
   * Returns the hash code of the vertex.
   * The hash code is computed based on the name of the vertex.
   *
   * @return the hash code of the vertex
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  /**
   * Returns a string representation of the vertex.
   * The string representation is the name of the vertex.
   *
   * @return the string representation of the vertex
   */
  @Override
  public String toString() {
    return this.name;
  }
}
