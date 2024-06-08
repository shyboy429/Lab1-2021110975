package basis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * ClassName:basis.GraphGenerate
 * Package:PACKAGE_NAME
 * Description: This class provides functionalities to generate a directed graph from a file.
 *
 * @author shyboy
 * @version 1.0
 * @date: 2024/5/13 17:15
 */
public class GraphGenerate {

  /**
   * Generates a directed graph from the specified file.
   * (For external use)
   *
   * @param fileName the name of the file to generate the graph from.
   * @return the generated graph.
   */
  public static Graph genGraph(String fileName) {
    filePreprocessing(fileName);
    return privateGenGraph(fileName + ".txt");
  }

  /**
   * Preprocesses the file by removing symbols and other unwanted characters.
   *
   * @param fileName the name of the file to preprocess.
   */
  private static void filePreprocessing(String fileName) {
    // Input and output file paths
    String inputFilePath = fileName;
    String outputFilePath = fileName + ".txt";
    try (
        // Create input stream
        FileReader fileReader = new FileReader(inputFilePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        // Create output stream
        FileWriter fileWriter = new FileWriter(outputFilePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
    ) {
      // Read and process the input file content
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // Replace punctuation with spaces, ignore non-letter characters, convert to lowercase
        String processedLine = line.replaceAll("[^a-zA-Z ,.!$%:]", "")
            .replaceAll("[,.!$%:]", " ")
            .toLowerCase();
        // Write the processed line to the output file
        bufferedWriter.write(processedLine);
        bufferedWriter.newLine(); // Write a newline character
      }
    } catch (IOException e) {
      System.err.println("File processing error: " + e.getMessage());
    }
  }

  /**
   * Generates a directed graph from the preprocessed file.
   *
   * @param fileName the name of the preprocessed file.
   * @return the generated graph.
   */
  private static Graph privateGenGraph(String fileName) {
    Graph graph = new Graph();
    try (Scanner scan = new Scanner(new FileInputStream(fileName))) {
      String preName = null;
      if (scan.hasNext()) {
        preName = scan.next();
      }
      if (preName != null) {
        graph.addVertex(preName);
      }
      while (scan.hasNext()) {
        String curName = scan.next();
        if (curName != null) {
          graph.addVertex(curName);
          graph.addEdge(preName, curName);
          preName = curName;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return graph;
  }
}
