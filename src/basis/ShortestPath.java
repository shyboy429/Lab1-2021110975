package basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * This class provides methods to compute the shortest path between vertices
 * in a graph using Dijkstra's algorithm.
 *
 * @date: 2024/5/13 21:32
 */
public class ShortestPath {

  private Graph graph;

  /**
   * Constructs a ShortestPath object with an empty graph.
   */
  public ShortestPath() {
    this.graph = new Graph();
  }

  /**
   * Constructs a ShortestPath object with the specified graph.
   *
   * @param graph the graph to be used for finding shortest paths
   */
  public ShortestPath(Graph graph) {
    this.graph = new Graph(graph); // Create a deep copy of the graph
  }

  /**
   * Computes the shortest path(s) between two vertices in the graph.
   *
   * @param word1 the name of the start vertex
   * @param word2 the name of the end vertex
   * @return a string representation of the shortest path(s) from word1 to word2
   */
  public String calcShortestPath(String word1, String word2) {
    ArrayList<Vertex> vertices = graph.getVertices();
    if (word1 == null && word2 == null) {
      return "Invalid start and end points!";
    } else if (word1 == null) {
      if(word2.contains(" ")){
        return "Invalid input! Word1 contains Spaces";
      }
      Vertex word = getVertexByName(vertices, word2);
      if (word == null) {
        return "There is no such vertex in the graph.";
      }
      StringBuilder word2ToAllPath = new StringBuilder();
      for (Vertex v : vertices) {
        if (!v.getName().equals(word2)) {
          String word2TovPath = calcShortestPath(word2, v.getName());
          word2ToAllPath.append("To ").append(v.getName()).append(" : ").append("\n")
              .append(word2TovPath).append("\n\n");
        }
      }
      word2ToAllPath.delete(word2ToAllPath.length() - 2, word2ToAllPath.length());
      return word2ToAllPath.toString();
    } else if (word2 == null) {
      if(word1.contains(" ")){
        return "Invalid input! Word1 contains Spaces";
      }
      Vertex word = getVertexByName(vertices, word1);
      if (word == null) {
        return "There is no such vertex in the graph.";
      }
      StringBuilder word1ToAllPath = new StringBuilder();
      for (Vertex v : vertices) {
        if (!v.getName().equals(word1)) {
          String word1TovPath = calcShortestPath(word1, v.getName());
          word1ToAllPath.append("To ").append(v.getName()).append(" : ").append("\n")
              .append(word1TovPath).append("\n\n");
        }
      }
      word1ToAllPath.delete(word1ToAllPath.length() - 2, word1ToAllPath.length());
      return word1ToAllPath.toString();
    }

    if(word1.contains(" ") && word2.contains(" ")){
      return "Invalid input! Word1 and word2 contains Spaces";
    }else if(word1.contains(" ")){
      return "Invalid input! Word1 contains Spaces";
    }else if(word2.contains(" ")){
      return "Invalid input! Word2 contains Spaces";
    }
    // 获取起点和终点节点对象
    Vertex start = getVertexByName(vertices, word1);
    Vertex end = getVertexByName(vertices, word2);
    if (start == null && end == null) {
      return "There is no such two vertex in the graph.";
    }
    if (start == null) {
      return "There is no such start vertex in the graph.";
    }
    if (end == null) {
      return "There is no such end vertex in the graph.";
    }

    // Dijkstra 算法初始化
    Map<Vertex, Integer> dist = new HashMap<>();
    Map<Vertex, List<Vertex>> prevs = new HashMap<>();
    PriorityQueue<Vertex> pq = new PriorityQueue<>((a, b) -> dist.get(a) - dist.get(b));
    for (Vertex v : vertices) {
      dist.put(v, Integer.MAX_VALUE);
      prevs.put(v, new ArrayList<>());
    }
    dist.put(start, 0);
    pq.offer(start);

    // Dijkstra 算法
    while (!pq.isEmpty()) {
      // 从优先队列中取出距离最小的节点
      Vertex u = pq.poll();
      // 如果取出的节点是目标节点，跳出循环
      if (u.equals(end)) {
        break;
      }
      // 遍历当前节点的相邻节点
      for (Vertex v : u.getNextvSet()) {
        // 计算经当前节点到相邻节点的距离
        int alt = dist.get(u) + u.weight.get(v);
        // 如果新计算的距离比原先记录的距离小
        if (alt < dist.get(v)) {
          // 更新最短距离
          dist.put(v, alt);
          // 更新前驱节点列表为当前节点
          prevs.get(v).clear();
          prevs.get(v).add(u);
          // 将相邻节点加入优先队列
          pq.offer(v);
        } else if (alt == dist.get(v)) {
          // 将当前节点加入前驱节点列表
          prevs.get(v).add(u);
        }
      }
    }

    // 构建所有最短路径字符串
    Set<String> paths = new HashSet<>();
    buildShortestPaths(start, end, new Stack<>(), prevs, paths);
    if (paths.isEmpty()) {
      return "It is not possible to get from " + word1 + " to " + word2 + "!";
    }
    Set<String> reversedPaths = reversePathStrings(paths);
    return String.join("\n", reversedPaths);
  }

  /**
   * Recursively builds the shortest path(s) from start to end.
   *
   * @param start the start vertex
   * @param end   the end vertex
   * @param path  the current path
   * @param prevs the map of previous vertices for each vertex
   * @param paths the set of all shortest paths
   */
  private void buildShortestPaths(Vertex start, Vertex end, Stack<Vertex> path,
                                  Map<Vertex, List<Vertex>> prevs, Set<String> paths) {
    // 将当前节点加入路径
    path.push(end);
    // 如果当前节点是起始节点，则构建完整路径并加入最短路径集合
    if (start.equals(end)) {
      StringBuilder sb = new StringBuilder();
      for (Vertex v : path) {
        sb.append(v.getName());
        if (v != path.peek()) {
          sb.append("->");
        }
      }
      paths.add(sb.toString());
    } else {
      // 遍历当前节点的前驱节点列表，递归构建路径
      for (Vertex prev : prevs.get(end)) {
        buildShortestPaths(start, prev, path, prevs, paths);
      }
    }
    // 回溯，将当前节点弹出路径
    path.pop();
  }

  /**
   * Returns the vertex with the specified name.
   *
   * @param vertices the list of vertices
   * @param name     the name of the vertex
   * @return the vertex with the specified name, or null if not found
   */
  private Vertex getVertexByName(ArrayList<Vertex> vertices, String name) {
    for (Vertex v : vertices) {
      if (v.getName().equals(name)) {
        return v;
      }
    }
    return null;
  }

  /**
   * Reverses the direction of all paths in the set.
   *
   * @param paths the set of paths to be reversed
   * @return a set of reversed paths
   */
  public static Set<String> reversePathStrings(Set<String> paths) {
    Set<String> reversedPaths = new HashSet<>();
    for (String path : paths) {
      String[] nodes = path.split("->");
      StringBuilder sb = new StringBuilder();
      for (int i = nodes.length - 1; i >= 0; i--) {
        sb.append(nodes[i]);
        if (i > 0) {
          sb.append("->");
        }
      }
      reversedPaths.add(sb.toString());
    }
    return reversedPaths;
  }
}
