package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import basis.Graph;
import basis.ShortestPath;
import org.junit.Test;

/**
 * 最短路径算法黑盒测试.
 */
public class ShortestPathTest {

  @Test
  public void testBothWordsExist() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("apple", "banana");
    System.out.println("测试两个单词都存在,第一个单词为一个，第二个单词为一个: " + result);
    assertTrue(result.contains("apple->banana"));
  }

  @Test
  public void testFirstWordExistsSecondWordNotExist() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("apple", "orange");
    System.out.println("测试第一个单词存在，第二个单词不存在: " + result);
    assertEquals("There is no such end vertex in the graph.", result);
  }

  @Test
  public void testFirstWordNotExistSecondWordExists() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("orange", "banana");
    System.out.println("测试第一个单词不存在，第二个单词存在: " + result);
    assertEquals("There is no such start vertex in the graph.", result);
  }

  @Test
  public void testBothWordsNotExist() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("orange", "grape");
    System.out.println("测试两个单词都不存在: " + result);
    assertEquals("There is no such two vertex in the graph.", result);
  }

  @Test
  public void testNullStart() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath(null, "banana");
    System.out.println("测试起点为null: " + result);
    assertEquals("To apple : \n"
        + "It is not possible to get from banana to apple!\n"
        + "\n"
        + "To cherry : \n"
        + "It is not possible to get from banana to cherry!\n"
        + "\n"
        + "To date : \n"
        + "banana->date", result);
  }

  //  @Test
  //  public void testFirstWordCountOne() {
  //    Graph graph = createSampleGraph();
  //    ShortestPath shortestPath = new ShortestPath(graph);
  //    String result = shortestPath.calcShortestPath("apple", "banana");
  //    System.out.println("测试第一个单词的个数为1: " + result);
  //    assertTrue(result.contains("apple->banana"));
  //  }

  @Test
  public void testFirstWordCountMultiple() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("apple banana", "banana");
    System.out.println("测试第一个单词的个数为多个: " + result);
    assertEquals("Invalid input! Word1 contains Spaces", result);
  }

  @Test
  public void testNullEnd() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("banana", null);
    System.out.println("测试终点为null: " + result);
    assertEquals("To apple : \n"
        + "It is not possible to get from banana to apple!\n"
        + "\n"
        + "To cherry : \n"
        + "It is not possible to get from banana to cherry!\n"
        + "\n"
        + "To date : \n"
        + "banana->date", result);
  }

  //  @Test
  //  public void testSecondWordCountOne() {
  //    Graph graph = createSampleGraph();
  //    ShortestPath shortestPath = new ShortestPath(graph);
  //    String result = shortestPath.calcShortestPath("apple", "banana");
  //    System.out.println("测试第二个单词的个数为1: " + result);
  //    assertTrue(result.contains("apple->banana"));
  //  }

  @Test
  public void testSecondWordCountMultiple() {
    Graph graph = createSampleGraph();
    ShortestPath shortestPath = new ShortestPath(graph);
    String result = shortestPath.calcShortestPath("apple", "apple banana");
    System.out.println("测试第二个单词的个数为多个: " + result);
    assertEquals("Invalid input! Word2 contains Spaces", result);
  }

  // 辅助方法，用于创建测试用的样本图。
  private Graph createSampleGraph() {
    Graph graph = new Graph();
    graph.addVertex("apple");
    graph.addVertex("banana");
    graph.addVertex("cherry");
    graph.addVertex("date");
    graph.addEdge("apple", "banana");
    graph.addEdge("apple", "cherry");
    graph.addEdge("banana", "date");
    graph.addEdge("cherry", "date");
    return graph;
  }
}
