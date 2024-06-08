package basis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.control.TextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * Class RandomWalk.
 * Provides a GUI for controlling and displaying a random walk on a graph.
 */
public class RandomWalk extends JFrame {

  private ArrayList<Vertex> vertices;  // List of vertices in the directed graph
  private boolean stopped;             // Flag to indicate if the walk is stopped
  private TextArea console;
  private boolean suspended;           // Flag to indicate if the thread is paused
  private StringBuilder result;        // StringBuilder to store the random walk result

  /**
   * Random walk.
   *
   * @param graph   graph
   * @param console console
   */
  public RandomWalk(Graph graph, TextArea console) {
    this.vertices = graph.getVertices();
    this.stopped = false;
    this.suspended = false;
    this.result = new StringBuilder();
    this.console = console;
    setTitle("Random Walk Control");

    // Create the continue button
    JButton continueButton = new JButton("Continue");
    continueButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        resumeWalk();
      }
    });

    // Create the pause button
    JButton stopButton = new JButton("Pause");
    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        suspendWalk();
      }
    });

    // Style buttons
    continueButton.setBackground(new Color(20, 196, 254));
    continueButton.setForeground(Color.BLACK);
    continueButton.setFocusPainted(false);
    continueButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    continueButton.setPreferredSize(new Dimension(200, 80));

    stopButton.setBackground(new Color(199, 236, 204));
    stopButton.setForeground(Color.BLACK);
    stopButton.setFocusPainted(false);
    stopButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    stopButton.setPreferredSize(new Dimension(200, 80));

    // Set layout and add buttons
    setLayout(new BorderLayout());
    add(stopButton, BorderLayout.SOUTH);
    add(continueButton, BorderLayout.NORTH);
    setSize(300, 200);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close current window only
    setLocationRelativeTo(null); // Center the window on the screen
    setVisible(true);
  }

  /**
   * Pauses the thread.
   */
  public synchronized void suspendWalk() {
    suspended = true;
  }

  /**
   * Resumes the thread.
   */
  public synchronized void resumeWalk() {
    suspended = false;
    notify();
  }

  /**
   * Starts a random walk on the graph.
   *
   * @return the result of the random walk.
   */
  public String randomWalk() {
    if (vertices == null || vertices.isEmpty()) {
      return "The vertex list is empty or null.";
    }

    HashMap<Vertex, HashSet<Vertex>> walkedVertices = new HashMap<>();
    for (Vertex v : vertices) {
      walkedVertices.put(v, new HashSet<>());
    }

    Vertex pre = vertices.get(new Random().nextInt(vertices.size()));
    Vertex next;
    result.append(pre.getName());
    System.out.print("Random walk starts:\n" + pre.getName());

    while (!stopped) {
      synchronized (this) {
        while (suspended) {
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }

      try {
        Thread.sleep(1000);  // Simulate time interval between steps
      } catch (InterruptedException e) {
        e.printStackTrace();
        return result.toString();
      }

      next = randomSelect(pre.getNextVSet());
      if (next == null) {
        result.append("\nNo successor node to walk to!");
        System.out.print("\nNo successor node to walk to!");
        break;
      }
      if (!suspended) {
        result.append("->").append(next.getName());
        System.out.print("->" + next.getName());
        if (walkedVertices.get(pre).contains(next)) {
          result.append("\nWalked through a repeated edge, stopping!");
          System.out.print("\nWalked through a repeated edge, stopping!");
          break;
        }
        walkedVertices.get(pre).add(next);
        pre = next;
      }
    }
    console.setText(result.toString());
    return result.toString();
  }

  /**
   * Selects a random vertex from a set of vertices.
   *
   * @param set the set of vertices to select from.
   * @return the randomly selected vertex, or null if the set is empty.
   */
  public Vertex randomSelect(Set<Vertex> set) {
    if (set == null || set.isEmpty()) {
      return null;
    }
    int random = new Random().nextInt(set.size());
    ArrayList<Vertex> list = new ArrayList<>(set);
    return list.get(random);
  }

  /**
   * Writes the random walk path to a file.
   *
   * @param randomWalkPath the path of the random walk.
   */
  public void writeRandomWalkPathToFile(String randomWalkPath) {
    // Get the default directory of the current project
    String currentDirectory = System.getProperty("user.dir");
    // Create the path.txt file path
    File file = new File(currentDirectory, "path.txt");

    // Use BufferedWriter to write to the file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(randomWalkPath);
      System.out.println("\nSuccessfully written to disk! Path: " + file.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
