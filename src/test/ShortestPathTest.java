package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import basis.Graph;
import basis.ShortestPath;
import org.junit.Before;
import org.junit.Test;


/**
 * ShortestPathTest.
 */
public class ShortestPathTest {
  private ShortestPath shortestPath;

  @Before
  public void setUp() throws Exception {
    shortestPath = new ShortestPath(newGraph());
  }

  @Test
  public void testWord1AndWord2AreNull() {
    String s = shortestPath.calcShortestPath(null, null);
    assertEquals("Invalid start and end points!", s);
  }

  @Test
  public void testWord1NullWord2Exist() {
    String result = shortestPath.calcShortestPath(null, "apple");
    assertTrue(result.contains("To banana : \napple->banana"));
    assertTrue(result.contains("To orange : \napple->orange"));
    assertTrue(result.contains("To grape : \napple->banana->grape"));
    assertTrue(result.contains("To peer : \napple->orange->peer\napple->banana->peer")
        || result.contains("To peer : " + "\napple->banana->peer\napple->orange->peer"));
  }

  @Test
  public void testWord1ExistWord2Null() {
    String result = shortestPath.calcShortestPath("apple", null);
    assertTrue(result.contains("To banana :"));
    assertTrue(result.contains("To orange :"));
    assertTrue(result.contains("To grape :"));
    assertTrue(result.contains("To peer :"));
  }

  @Test
  public void testWord1NullAndWord2NotExist() {
    String s = shortestPath.calcShortestPath(null, "hit");
    assertEquals("There is no such vertex in the graph.", s);
  }

  @Test
  public void testWord1NotExistAndWord2Null() {
    String s = shortestPath.calcShortestPath("hit", null);
    assertEquals("There is no such vertex in the graph.", s);
  }


  @Test
  public void testBothWordsNotExist() {
    String result = shortestPath.calcShortestPath("kiwi", "melon");
    assertEquals("There is no such two vertex in the graph.", result);
  }

  @Test
  public void testStartWordNotExist() {
    String result = shortestPath.calcShortestPath("kiwi", "banana");
    assertEquals("There is no such start vertex in the graph.", result);
  }

  @Test
  public void testEndWordNotExist() {
    String result = shortestPath.calcShortestPath("apple", "melon");
    assertEquals("There is no such end vertex in the graph.", result);
  }

  @Test
  public void testOneShortestPath() {
    String result = shortestPath.calcShortestPath("apple", "peer");
    assertEquals("apple->banana->peer\napple->orange->peer", result);
  }

  @Test
  public void testMultiShortestPathTest() {
    String result = shortestPath.calcShortestPath("apple", "grape");
    assertTrue(result.contains("apple->banana->grape"));
  }

  @Test
  public void testSameStartAndEnd() {
    String result = shortestPath.calcShortestPath("apple", "apple");
    assertEquals("apple", result);
  }

  @Test
  public void testNoPathBetweenTwoWords() {
    String result = shortestPath.calcShortestPath("banana", "apple");
    assertEquals("It is not possible to get from banana to apple!", result);
  }

  private Graph newGraph() {
    Graph graph = new Graph();
    graph.addVertex("apple");
    graph.addVertex("banana");
    graph.addVertex("orange");
    graph.addVertex("grape");
    graph.addVertex("peer");
    graph.addEdge("apple", "banana");
    graph.addEdge("apple", "orange");
    graph.addEdge("orange", "peer");
    graph.addEdge("banana", "grape");
    graph.addEdge("banana", "peer");
    return graph;
  }
}
