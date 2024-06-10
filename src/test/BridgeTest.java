package test;

import basis.Bridge;
import basis.Graph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BridgeTest {
  private Bridge bridgeTestGraph;

  @Before
  public void setUp() {
    bridgeTestGraph = new Bridge(newGraph());
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

  @Test
  public void testQueryBridgeWords_Word1Null() {
    String result = bridgeTestGraph.queryBridgeWords(null, "banana");
    assertEquals("word1 is null!", result);
  }

  @Test
  public void testQueryBridgeWords_Word2Null() {
    String result = bridgeTestGraph.queryBridgeWords("apple", null);
    assertEquals("word2 is null!", result);
  }
  
  @Test
  public void testQueryBridgeWords_BothWordsNull() {
    String result = bridgeTestGraph.queryBridgeWords(null, null);
    assertEquals("word1 and word2 are null!", result);
  }
  
  @Test
  public void testQueryBridgeWords_BothWordsNotInGraph() {
    String result = bridgeTestGraph.queryBridgeWords("kiwi", "mango");
    assertEquals("No \"kiwi\" and \"mango\" in the graph!", result);
  }

  @Test
  public void testQueryBridgeWords_Word1NotInGraph() {
    String result = bridgeTestGraph.queryBridgeWords("kiwi", "banana");
    assertEquals("No \"kiwi\" in the graph!", result);
  }

  @Test
  public void testQueryBridgeWords_Word2NotInGraph() {
    String result = bridgeTestGraph.queryBridgeWords("apple", "mango");
    assertEquals("No \"mango\" in the graph!", result);
  }

  @Test
  public void testQueryBridgeWords_NoBridgeWords() {
    String result = bridgeTestGraph.queryBridgeWords("apple", "banana");
    assertEquals("No bridge words from \"apple\" to \"banana\"!", result);
  }

  @Test
  public void testQueryBridgeWords_SingleBridgeWord() {
    String result = bridgeTestGraph.queryBridgeWords("apple", "grape");
    assertEquals("The bridge words from \"apple\" to \"grape\" is: \"banana\".", result);
  }
  
  @Test
  public void testQueryBridgeWords_MultiBridgeWords() {
    String result = bridgeTestGraph.queryBridgeWords("apple", "peer");
    assertEquals("The bridge words from \"apple\" to \"peer\" are: \"banana\", \"orange\".", result);
  }

  
}
