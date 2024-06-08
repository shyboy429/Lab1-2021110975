package basis;

import java.util.ArrayList;

/**
 * ClassName:basis.Bridge
 * Package:PACKAGE_NAME
 * Description: This class represents a bridge that connects words in a graph.
 *
 * @author shyboy
 * @version 1.0
 * @date: 2024/5/13 20:36
 */
public class Bridge {

  /**
   * Represents the graph that connects words.
   */
  private Graph graph;

  /**
   * Constructs a Bridge object with an empty graph.
   */
  public Bridge() {
    this.graph = new Graph();
  }

  /**
   * Constructs a Bridge object with the given graph.
   *
   * @param graph the graph that connects words.
   */
  public Bridge(Graph graph) {
    this.graph = graph;
  }

  /**
   * Returns the graph.
   *
   * @return the graph that connects words.
   */
  public Graph getGraph() {
    return graph;
  }

  /**
   * Sets the graph.
   *
   * @param graph the graph that connects words.
   */
  public void setGraph(Graph graph) {
    this.graph = graph;
  }

  /**
   * Queries the bridge words between two given words.
   *
   * @param word1 the first word.
   * @param word2 the second word.
   * @return a string containing the bridge words if found, otherwise an appropriate error message.
   */
  public String queryBridgeWords(String word1, String word2) {
    if (word1 == null && word2 == null) {
      return "word1 and word2 are null!";
    }
    if (word1 == null) {
      return "word1 is null!";
    }
    if (word2 == null) {
      return "word2 is null!";
    }

    ArrayList<Vertex> vertices = graph.getVertices();
    boolean word1Found = false;
    boolean word2Found = false;

    // Check if word1 and word2 exist in the graph
    for (Vertex v : vertices) {
      if (v.getName().equals(word1)) {
        word1Found = true;
      }
      if (v.getName().equals(word2)) {
        word2Found = true;
      }
    }

    // Return appropriate message if word1 or word2 is not found
    if (!word1Found && !word2Found) {
      return "No \"" + word1 + "\" and \"" + word2 + "\" in the graph!";
    } else if (!word1Found) {
      return "No \"" + word1 + "\" in the graph!";
    } else if (!word2Found) {
      return "No \"" + word2 + "\" in the graph!";
    }

    // Check for bridge words
    ArrayList<String> bridgeWords = new ArrayList<>();
    for (Vertex v : vertices) {
      for (Vertex preVertex : v.getPrevSet()) {
        if (preVertex.getName().equals(word1)) {
          for (Vertex nextVertex : v.getNextvSet()) {
            if (nextVertex.getName().equals(word2)) {
              bridgeWords.add(v.getName());
            }
          }
        }
      }
    }

    // Return appropriate message if no bridge words are found
    if (bridgeWords.isEmpty()) {
      return "No bridge words from \"" + word1 + "\" to \"" + word2 + "\"!";
    }

    // Construct the output string
    StringBuilder result = new StringBuilder("The bridge words from \"" + word1 
        + "\" to \"" + word2 + "\" are: \"");
    for (int i = 0; i < bridgeWords.size(); i++) {
      result.append(bridgeWords.get(i));
      if (i < bridgeWords.size() - 1) {
        result.append("\", \"");
      }
    }
    result.append("\".");
    return result.toString();
  }

  /**
   * Generates new text by inserting bridge words between consecutive words.
   *
   * @param inputText the input text.
   * @return the new text with bridge words inserted.
   */
  private String privateGenerateNewText(String inputText) {
    String[] words = inputText.split("\\s+"); // Split input text into words
    StringBuilder newText = new StringBuilder();

    // Traverse the words in the input text
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      String word2 = words[i + 1];
      newText.append(word1); // Add the current word to the new text

      // Check for bridge words between word1 and word2
      String bridgeWord = privateQueryBridgeWords(word1, word2);
      if (bridgeWord != null) {
        // Insert the bridge word into the new text
        newText.append(" ").append(bridgeWord).append(" ");
      } else {
        // Add a space after the current word if no bridge word is found
        newText.append(" ");
      }
    }

    // Add the last word to the new text
    newText.append(words[words.length - 1]);
    return newText.toString();
  }

  /**
   * Generates new text in a private environment 
   * by inserting bridge words between consecutive words.
   *
   * @param inputText the input text.
   * @return the new text with bridge words inserted.
   */
  public String generateNewText(String inputText) {
    String t1 = privateGenerateNewText(inputText);
    String t2 = privateGenerateNewText(t1);
    while (!t1.equals(t2)) {
      t1 = t2;
      t2 = privateGenerateNewText(t1);
    }
    return t1;
  }

  /**
   * Queries the bridge words between two given words in a private environment.
   *
   * @param word1 the first word.
   * @param word2 the second word.
   * @return the bridge word if found, otherwise null.
   */
  private String privateQueryBridgeWords(String word1, String word2) {
    ArrayList<Vertex> vertices = graph.getVertices();
    // Check for bridge words
    for (Vertex v : vertices) {
      for (Vertex preVertex : v.getPrevSet()) {
        if (preVertex.getName().equals(word1)) {
          for (Vertex nextVertex : v.getNextvSet()) {
            if (nextVertex.getName().equals(word2)) {
              return v.getName();
            }
          }
        }
      }
    }
    return null;
  }
}
