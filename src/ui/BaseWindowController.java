package ui;


import basis.Bridge;
import basis.Graph;
import basis.GraphGenerate;
import basis.GraphVisualizer;
import basis.RandomWalk;
import basis.ShortestPath;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


class BaseWindowController {

  private static final Logger logger = Logger.getLogger(BaseWindowController.class.getName());
  private Graph graph;

  private String content;
  @FXML
  private MenuBar menuBar;                //菜单栏
  @FXML
  private Button textButton;            //“查看源文本”按钮
  @FXML
  private Button showButton;            //“展示有向图”按钮
  @FXML
  private Button queryButton;            //“查询桥接词”按钮
  @FXML
  private Button generateButton;        //“生成新文本”按钮
  @FXML
  private Button pathButton;            //“求最短路径”按钮
  @FXML
  private Button walkButton;            //“随机游走”按钮

  @FXML
  private TextArea console;                //控制台，用于显示各种信息
  @FXML
  private StackPane stackPane;            //控制按钮面板的容器


  private String filepath;

  /*
   * “打开”菜单项被点击时的事件处理方法
   */
  @FXML
  protected void handleOpenMenuItemClicked() {
    Stage stage; // 获取当前窗口
    stage = (Stage) menuBar.getScene().getWindow();
    FileChooser fileChooser = new FileChooser(); // 文件选择器

    fileChooser.setTitle("打开文件"); // 设置窗口标题
    fileChooser.setInitialDirectory(new File("C:\\Users\\86139\\Desktop\\SELAB1")); // 设置初始路径
    // 设置文件格式过滤器
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("文本文档", "*.txt"),
        new FileChooser.ExtensionFilter("所有文件", "*.*"));
    File file = fileChooser.showOpenDialog(stage); // 打开文件选择对话框
    // 记录选择的文件对象
    if (file != null) {
      try (Scanner scan = new Scanner(file)) {
        String content = scan.useDelimiter("\\Z").next(); // 读取文件全部内容
        this.content = content;
        console.setText(content); // 将内容显示到控制台
        String filePath = file.getAbsolutePath(); // 获取用户选择的文件路径
        Graph graph = GraphGenerate.genGraph(filePath); // 使用选择的文件路径生成图
        this.filepath = filePath;
        this.graph = graph;
        //showButton.setDisable(false);
      } catch (FileNotFoundException err) {
        logger.log(Level.SEVERE, "An exception occurred", err);
      }
      //成功生成有向图后，各功能控制按钮可用
      if (graph != null) {
        textButton.setDisable(false);
        showButton.setDisable(false);
        queryButton.setDisable(false);
        generateButton.setDisable(false);
        pathButton.setDisable(false);
        walkButton.setDisable(false);
      }
    }
  }

  /*
   * “关闭”菜单项被点击时的事件处理方法
   */
  @FXML
  protected void handleCloseMenuItemClicked() {
    Stage stage = (Stage) menuBar.getScene().getWindow();    //获取主窗口
    stage.close();    //关闭主窗口
  }

  @FXML
  protected void handdleTextButtonClicked() {
    String filepath = this.filepath + ".txt";
    try (Scanner scanner = new Scanner(new File(filepath))) {
      this.content = scanner.useDelimiter("\\Z").next();
    } catch (FileNotFoundException err) {
      logger.log(Level.SEVERE, "An exception occurred", err);
    }
    console.setText(this.content);
  }

  @FXML
  protected void handleShowButtonClicked() {
    GraphVisualizer.showDirectGraph(this.graph);
  }

  //查询桥接词
  @FXML
  protected void handleQueryButtonClicked() throws Exception {
    GridPane prePane;
    prePane = (GridPane) stackPane.getChildren().get(0);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("PathPane.fxml"));
    GridPane pane;
    pane = loader.load();
    TextField word1Tf;
    word1Tf = (TextField) loader.getNamespace().get("word1TextField");
    TextField word2Tf;
    word2Tf = (TextField) loader.getNamespace().get("word2TextField");
    Button returnBt;
    returnBt = (Button) loader.getNamespace().get("returnButton");
    Button yesBt;
    yesBt = (Button) loader.getNamespace().get("yesButton");
    stackPane.getChildren().remove(prePane);
    stackPane.getChildren().add(pane);
    //“返回”按钮被点击时，重新显示控制按钮面板
    returnBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stackPane.getChildren().remove(pane);
        stackPane.getChildren().add(prePane);
      }
    });
    //“确定”按钮被点击时，查询桥接词并显示
    yesBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        String word1 = word1Tf.getText().trim();
        String word2 = word2Tf.getText().trim();
        if (word1.isEmpty()) {
          word1 = null;
        }
        if (word2.isEmpty()) {
          word2 = null;
        }
        Bridge bri = new Bridge(BaseWindowController.this.graph);
        String result = bri.queryBridgeWords(word1, word2);

        console.setText(result);

      }
    });


  }

  //生成新文本
  @FXML
  protected void handleGenerateButtonClicked() throws Exception {
    GridPane prePane = (GridPane) stackPane.getChildren().get(0);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("gentext.fxml"));
    GridPane pane = loader.load();
    TextField word1Tf = (TextField) loader.getNamespace().get("word1TextField");
    Button returnBt = (Button) loader.getNamespace().get("returnButton");
    Button yesBt = (Button) loader.getNamespace().get("yesButton");
    //“返回”按钮被点击时，重新显示控制按钮面板
    returnBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stackPane.getChildren().remove(pane);
        stackPane.getChildren().add(prePane);
      }
    });
    //“确定”按钮被点击时，求最短路径并显示
    yesBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        String word1 = word1Tf.getText().trim();
        Bridge bri = new Bridge(BaseWindowController.this.graph);
        String result = bri.generateNewText(word1);

        console.setText(result);

      }
    });
    stackPane.getChildren().remove(prePane);
    stackPane.getChildren().add(pane);
  }

  //最短路径
  @FXML
  protected void handlePathButtonClicked() throws Exception {
    GridPane prePane = (GridPane) stackPane.getChildren().get(0);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("PathPane.fxml"));
    GridPane pane = loader.load();
    TextField word1Tf = (TextField) loader.getNamespace().get("word1TextField");
    TextField word2Tf = (TextField) loader.getNamespace().get("word2TextField");
    Button returnBt = (Button) loader.getNamespace().get("returnButton");
    Button yesBt = (Button) loader.getNamespace().get("yesButton");
    //“返回”按钮被点击时，重新显示控制按钮面板

    returnBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stackPane.getChildren().remove(pane);
        stackPane.getChildren().add(prePane);
      }
    });
    //“确定”按钮被点击时，求最短路径并显示
    yesBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {

        String word1 = word1Tf.getText().trim();
        String word2 = word2Tf.getText().trim();
        if (word1.isEmpty()) {
          word1 = null;
        }
        if (word2.isEmpty()) {
          word2 = null;
        }
        ShortestPath sp = new ShortestPath(BaseWindowController.this.graph);
        String result = sp.calcShortestPath(word1, word2);

        console.setText(result);
        // 按照换行符分割每一行
        String[] lines = result.split("\n");
        String substring = "->";
        boolean contains = lines[0].contains(substring);
        if (!contains) {
          return;
        }
        // 创建一个二维数组列表
        List<List<String>> twoDimensionalArray = new ArrayList<>();
        // 遍历每一行，按照'->'分割，并将结果添加到二维数组列表中
        for (String line : lines) {
          String[] parts = line.split("->");
          List<String> row = new ArrayList<>(Arrays.asList(parts));
          twoDimensionalArray.add(row);
        }
        if (word2 != null) {
          new GraphVisualizer(graph, twoDimensionalArray);
        }
      }
    });

    stackPane.getChildren().remove(prePane);
    stackPane.getChildren().add(pane);
  }


  @FXML
  protected void handleWalkButtonClicked() throws Exception {
    RandomWalk walker = new RandomWalk(this.graph, this.console);
    String randomWalkRes = walker.randomWalk();
    walker.writeRandomWalkPathToFile(randomWalkRes);
  }
}

