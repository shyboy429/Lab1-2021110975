package basis;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * GraphVisualizer.
 */
public class GraphVisualizer extends JFrame {

  private static final Logger logger = Logger.getLogger(GraphVisualizer.class.getName());

  // 定义颜色数组，这些颜色都是经典的，有区分度的颜色，且不包含黑色和白色
  private static final String[] colors = {
      "#FF6347", "#7FFFD4", "#FFD700", "#32CD32", "#4682B4", "#FF69B4", "#00BFFF", "#9370DB",
      "#008080",
      "#FFA07A", "#6A5ACD", "#FF4500", "#40E0D0", "#800080", "#00FF00", "#FF7F50", "#191970",
      "#00FA9A",
      "#B22222", "#F0E68C", "#8A2BE2", "#7CFC00", "#AFEEEE", "#778899", "#FF8C00", "#BA55D3",
      "#00FF7F",
      "#DAA520", "#FF00FF", "#8B008B", "#008000", "#FFDAB9", "#FA8072", "#20B2AA", "#800000",
      "#B0E0E6",
      "#808000", "#66CDAA", "#8B0000", "#2F4F4F", "#A52A2A", "#483D8B", "#7B68EE", "#F5DEB3",
      "#ADFF2F",
      "#7FFF00", "#CD5C5C", "#556B2F", "#D2691E", "#4682B4", "#5F9EA0", "#6495ED", "#228B22",
      "#BC8F8F",
      "#FF6347", "#87CEEB", "#F08080", "#20B2AA", "#FA8072", "#6B8E23", "#FFA07A", "#FF7F50",
      "#32CD32"
  };
  // 随机数生成器
  public mxGraph mxGraph = new mxGraph();
  private static final SecureRandom secureRandom = new SecureRandom();

  public static int getSecureRandomNumber(int bound) {
    return secureRandom.nextInt(bound);
  }

  /**
   * Graph visualizer.
   *
   * @param graph 有向图
   * @param paths 最短路径
   */
  public GraphVisualizer(Graph graph, List<List<String>> paths) {
    super("展示有向图");

    // 设置仅关闭当前窗口，而不退出整个应用程序
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Object parent = this.mxGraph.getDefaultParent();

    this.mxGraph.getModel().beginUpdate();
    try {
      // 遍历图中的每个顶点
      for (Vertex v : graph.getVertices()) {
        // 在图中插入一个顶点，并获取顶点对象
        int x = getSecureRandomNumber(900);
        int y = getSecureRandomNumber(1000);
        Object vertex = findVertexByName(this.mxGraph, v.getName());
        if (vertex == null) {
          int wordLength = v.getName().length();
          int width = Math.max(wordLength * 15, 30);
          int height = 30; // 固定高度
          vertex = this.mxGraph.insertVertex(parent, null, v.getName(), x, y, width, height);
          this.mxGraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFFFFF",
              new Object[]{vertex}); // 背景颜色为白色
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#000000",
              new Object[]{vertex}); // 字体颜色为黑色
          this.mxGraph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#000000",
              new Object[]{vertex}); // 边框颜色为黑色
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTFAMILY, "Times New Roman",
              new Object[]{vertex}); //
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTSIZE, "18", new Object[]{vertex});
          // 字体族设置为新罗马
          // 设置节点边框宽度
          this.mxGraph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1",
              new Object[]{vertex}); // 边框宽度为1

          this.mxGraph.setCellStyles(mxConstants.STYLE_LABEL_POSITION, "-20", new Object[]{vertex});
          this.mxGraph.setCellStyles(mxConstants.STYLE_VERTICAL_LABEL_POSITION,
              mxConstants.ALIGN_MIDDLE,
              new Object[]{vertex});
        }
        // 遍历当前顶点的每个邻居
        for (Vertex next : v.getNextvSet()) {
          // 判断邻居是否已经存在于图中
          int k = getSecureRandomNumber(900);
          int z = getSecureRandomNumber(1000);
          Object neighvertex = findVertexByName(this.mxGraph, next.getName());
          if (neighvertex == null) {
            int wordLength = next.getName().length();
            int width = Math.max(wordLength * 15, 30);
            int height = 30; // 固定高度
            neighvertex = this.mxGraph.insertVertex(parent, null, next.getName(), k, z, width,
                height);
            this.mxGraph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#FFFFFF",
                new Object[]{neighvertex}); //
            // 背景颜色为白色
            this.mxGraph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#000000",
                new Object[]{neighvertex}); //
            // 字体颜色为黑色
            this.mxGraph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#000000",
                new Object[]{neighvertex});
            // 边框颜色为黑色
            this.mxGraph.setCellStyles(mxConstants.STYLE_FONTFAMILY, "Times New Roman",
                new Object[]{neighvertex}); // 字体族设置为新罗马
            // 设置节点边框宽度
            this.mxGraph.setCellStyles(mxConstants.STYLE_FONTSIZE, "18", new Object[]{neighvertex});
            this.mxGraph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1",
                new Object[]{neighvertex}); // 边框宽度为1
            this.mxGraph.setCellStyles(mxConstants.STYLE_VERTICAL_LABEL_POSITION, "-100",
                new Object[]{neighvertex});
          }
          // 检查是否存在反向边
          this.mxGraph.getEdgesBetween(neighvertex, vertex);
          // 为每个邻居连接一条边
          Object edge;
          // 设置弧线样式
          edge = this.mxGraph.insertEdge(parent, null, String.valueOf(v.getWeight().get(next)),
              vertex,
              neighvertex,
              "edgeStyle=elbowEdgeStyle;elbow=horizontal;rounded=1;orthogonal=1;");

          this.mxGraph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#000000",
              new Object[]{edge}); // 边的颜色为黑色
          this.mxGraph.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "1.5",
              new Object[]{edge}); // 边的宽度为1.5
          Font font = new Font("Arial", Font.BOLD, 25); // 设置字体，Arial 字体族，粗体，大小 18
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTFAMILY, font.getFamily(),
              new Object[]{edge}); // 设置字体族
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTSIZE, String.valueOf(font.getSize()),
              new Object[]{edge}); // 设置字体大小
          this.mxGraph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "#000000",
              new Object[]{edge}); // 设置字体颜色为黑色
        }
      }
      //开始标注
      if (paths != null) {
        for (List<String> row : paths) {
          // 获取当前行的大小
          String color = generateColorCode();
          int size = row.size();
          // 遍历当前行的每个元素，但最后一个元素无法与其后的元素组成连续的一对
          for (int i = 0; i < size - 1; i++) {
            String currentElement = row.get(i);
            String nextElement = row.get(i + 1);
            // 打印当前元素和下一个元素
            this.highlightEdges(currentElement, nextElement, color);
          }
        }

      }
      // 创建自动布局对象，并执行布局

      mxGraphLayout layout = new mxHierarchicalLayout(this.mxGraph);

      //mxGraphLayout layout = new mxCompactTreeLayout(this.mxGraph);
      layout.execute(parent);
    } finally {
      this.mxGraph.getModel().endUpdate();
    }

    mxGraphComponent graphComponent = new mxGraphComponent(this.mxGraph);
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          saveGraphAsImage(graphComponent);
        }
      }
    });

    // 添加鼠标滚轮监听器以支持缩放
    graphComponent.getGraphControl().addMouseWheelListener(new MouseAdapter() {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
          double scale = graphComponent.getGraph().getView().getScale();
          int notches = e.getWheelRotation();
          double newScale = (notches < 0) ? scale * 1.1 : scale / 1.1;
          graphComponent.getGraph().getView().setScale(newScale);
        }
      }
    });

    getContentPane().add(graphComponent);
    setSize(990, 1100);
    setVisible(true);
  }

  public static void showDirectGraph(Graph graph) {
    new GraphVisualizer(graph, null);
  }

  // 生成随机颜色代码字符串
  public static String generateColorCode() {
    // 从颜色数组中随机选择一种颜色
    return colors[getSecureRandomNumber(colors.length)];
  }

  private Object findVertexByName(mxGraph graph, String name) {
    // 遍历图中所有的顶点，查找名称匹配的顶点
    for (Object cell : graph.getChildVertices(graph.getDefaultParent())) {
      if (name.equals(((mxCell) cell).getValue().toString())) {
        return cell;
      }
    }
    return null;
  }

  /**
   * 高亮最短路径.
   *
   * @param sourceNodeName sourceNodeName
   * @param targetNodeName targetNodeName
   * @param color          color
   */
  public void highlightEdges(String sourceNodeName, String targetNodeName, String color) {
    // 遍历图中所有的边
    Object[] edges = this.mxGraph.getChildEdges(this.mxGraph.getDefaultParent());
    for (Object edge : edges) {
      Object sourceVertex = this.mxGraph.getModel().getTerminal(edge, true);
      Object targetVertex = this.mxGraph.getModel().getTerminal(edge, false);
      // 找到源节点和目标节点的名称
      String sourceName = ((mxCell) sourceVertex).getValue().toString();
      String targetName = ((mxCell) targetVertex).getValue().toString();
      // 如果源节点是指定的源节点，并且目标节点是指定的目标节点，则将边的样式设置为红色
      if (sourceName.equals(sourceNodeName) && targetName.equals(targetNodeName)) {
        this.mxGraph.setCellStyles(mxConstants.STYLE_STROKECOLOR, color,
            new Object[]{edge}); // 将边的颜色设置为红色
      }
    }
  }


  private void saveGraphAsImage(mxGraphComponent graphComponent) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Graph as Image");
    fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
    fileChooser.setSelectedFile(new File("untitled.png"));
    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      String filePath = FilenameUtils.getName(fileToSave.getName());
      if (!filePath.toLowerCase().endsWith(".png")) {
        filePath += ".png";
      }

      // 限定保存路径到当前用户目录
      Path safeDir = Paths.get(System.getProperty("user.home"), "safe_dir");
      File safeDirFile = safeDir.toFile();
      if (!safeDirFile.exists()) {
        //safeDirFile.mkdirs();
        return;
      }

      Path safePath = safeDir.resolve(filePath);
      File safeFile = safePath.toFile();

      Dimension size = graphComponent.getGraphControl().getSize();
      BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g2 = image.createGraphics();

      g2.setColor(Color.WHITE);
      g2.fillRect(0, 0, size.width, size.height);

      graphComponent.getGraphControl().paint(g2);
      g2.dispose();

      try {
        ImageIO.write(image, "png", safeFile);
        System.out.println("Graph saved as " + safeFile.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}