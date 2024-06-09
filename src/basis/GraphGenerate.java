package basis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * ClassName:basis.GraphGenerate
 * Package:PACKAGE_NAME
 * Description: This class provides functionalities to generate a directed graph from a file.
 *
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
    Path inputPath = validateAndNormalizePath(fileName);
    if (inputPath == null) {
      throw new IllegalArgumentException("Invalid file path");
    }
    Path fileName1 = inputPath.getFileName();
    if (fileName1 == null) {
      throw new IllegalArgumentException("Invalid file path");
    }
    Path outputPath = inputPath.resolveSibling(fileName1 + ".txt");
    filePreprocessing(inputPath, outputPath);
    return privateGenGraph(outputPath);
  }

  /**
   * Validates and normalizes the file path to prevent path traversal attacks.
   *
   * @param fileName the name of the file to validate.
   * @return the validated and normalized Path object, or null if the path is invalid.
   */
  private static Path validateAndNormalizePath(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return null;
    }
    // Additional validation to ensure the path is within an expected directory
    // For example, checking if the path is within the current working directory
    Path currentDir = Paths.get("").toAbsolutePath().normalize();
    Path resolvedPath = currentDir.resolve(fileName).normalize();
    if (!resolvedPath.startsWith(currentDir)) {
      return null;
    }
    return resolvedPath;
  }

  /**
   * Preprocesses the file by removing symbols and other unwanted characters.
   *
   * @param inputPath  the path of the file to preprocess.
   * @param outputPath the path of the output file.
   */
  private static void filePreprocessing(Path inputPath, Path outputPath) {
    try (
        // Create input stream with explicit charset
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader(inputPath.toFile()));
        // Create output stream with explicit charset
        BufferedWriter bufferedWriter = new BufferedWriter(
            new FileWriter(outputPath.toFile()))
    ) {
      // Read and process the input file content
      String line;
      while ((line = bufferedReader.readLine()) != null) {
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
   * @param filePath the path of the preprocessed file.
   * @return the generated graph.
   */
  private static Graph privateGenGraph(Path filePath) {
    Graph graph = new Graph();
    try (Scanner scan = new Scanner(
        new FileInputStream(filePath.toFile()))) {
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
