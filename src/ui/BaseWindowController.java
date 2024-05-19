package ui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import basis.ShortestPath;
import basis.Bridge;

import javafx.scene.control.TextArea;
import basis.*;
import javafx.fxml.FXML;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 * 主窗口控制类
 *
 * @author XJL YSJ
 * @version 1.2.0
 * @date 2017-09-15
 */
public class BaseWindowController {
    private Graph graph;
    private GraphVisualizer printer;
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


    private File dataFile;                        //源文本文件对象


    /**
     * “打开”菜单项被点击时的事件处理方法
     */
    @FXML
    protected void handleOpenMenuItemClicked(ActionEvent e) {
        Stage stage = (Stage) menuBar.getScene().getWindow(); // 获取当前窗口
        FileChooser fileChooser = new FileChooser(); // 文件选择器

        fileChooser.setTitle("打开文件"); // 设置窗口标题
        fileChooser.setInitialDirectory(new File("C:\\Users\\86139\\Desktop\\SELAB1")); // 设置初始路径
        // 设置文件格式过滤器
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("文本文档", "*.txt"),
                new FileChooser.ExtensionFilter("所有文件", "*.*"));
        File file = fileChooser.showOpenDialog(stage); // 打开文件选择对话框
        dataFile = file; // 记录选择的文件对象
        if (file != null) {
            try (Scanner scan = new Scanner(file)) {
                String content = scan.useDelimiter("\\Z").next(); // 读取文件全部内容
                this.content = content;
                console.setText(content); // 将内容显示到控制台
                String filePath = file.getAbsolutePath(); // 获取用户选择的文件路径
                Graph graph = GraphGenerate.genGraph(filePath); // 使用选择的文件路径生成图
                this.graph = graph;
                //showButton.setDisable(false);
            } catch (FileNotFoundException err) {
                err.printStackTrace();
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

    /**
     * “关闭”菜单项被点击时的事件处理方法
     */
    @FXML
    protected void handleCloseMenuItemClicked(ActionEvent e) {
        Stage stage = (Stage) menuBar.getScene().getWindow();    //获取主窗口
        stage.close();    //关闭主窗口
    }
    @FXML
    protected void handdleTextButtonClicked (MouseEvent e){console.setText(this.content);}
    @FXML
    protected void handleShowButtonClicked(MouseEvent e) {
        GraphVisualizer.showDirectGraph(this.graph);
    }
    //查询桥接词
    @FXML
    protected void handleQueryButtonClicked(MouseEvent e) throws Exception {
        GridPane prePane = (GridPane)stackPane.getChildren().get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PathPane.fxml"));
        GridPane pane = loader.load();
        TextField word1TF = (TextField)loader.getNamespace().get("word1TextField");
        TextField word2TF = (TextField)loader.getNamespace().get("word2TextField");
        Button returnBT = (Button)loader.getNamespace().get("returnButton");
        Button yesBT = (Button)loader.getNamespace().get("yesButton");
        stackPane.getChildren().remove(prePane);
        stackPane.getChildren().add(pane);
        //“返回”按钮被点击时，重新显示控制按钮面板
        returnBT.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(pane);
            stackPane.getChildren().add(prePane);
        });
        //“确定”按钮被点击时，查询桥接词并显示
        yesBT.setOnMouseClicked(event -> {

            String word1 = word1TF.getText().trim();
            String word2 = word2TF.getText().trim();
            if(word1.equals("")){
                word1 = null;
            }
            if(word2.equals("")){
                word2 = null;
            }
            Bridge bri = new Bridge(this.graph);
            String result = bri.queryBridgeWords(word1,word2);

            console.setText(result);

        });


    }
    //生成新文本
    @FXML
    protected void handleGenerateButtonClicked(MouseEvent e) throws Exception {
        GridPane prePane = (GridPane)stackPane.getChildren().get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gentext.fxml"));
        GridPane pane = loader.load();
        TextField word1TF = (TextField)loader.getNamespace().get("word1TextField");
        Button returnBT = (Button)loader.getNamespace().get("returnButton");
        Button yesBT = (Button)loader.getNamespace().get("yesButton");
        //“返回”按钮被点击时，重新显示控制按钮面板
        returnBT.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(pane);
            stackPane.getChildren().add(prePane);
        });
        //“确定”按钮被点击时，求最短路径并显示
        yesBT.setOnMouseClicked(event -> {

            String word1 = word1TF.getText().trim();
            Bridge bri = new Bridge(this.graph);
            String result = bri.generateNewText(word1);

            console.setText(result);

        });
        stackPane.getChildren().remove(prePane);
        stackPane.getChildren().add(pane);
    }
    //最短路径
    @FXML
    protected void handlePathButtonClicked(MouseEvent e) throws Exception {
        GridPane prePane = (GridPane)stackPane.getChildren().get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PathPane.fxml"));
        GridPane pane = loader.load();
        TextField word1TF = (TextField)loader.getNamespace().get("word1TextField");
        TextField word2TF = (TextField)loader.getNamespace().get("word2TextField");
        Button returnBT = (Button)loader.getNamespace().get("returnButton");
        Button yesBT = (Button)loader.getNamespace().get("yesButton");
        //“返回”按钮被点击时，重新显示控制按钮面板
        returnBT.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(pane);
            stackPane.getChildren().add(prePane);
        });
        //“确定”按钮被点击时，求最短路径并显示
        yesBT.setOnMouseClicked(event -> {

            String word1 = word1TF.getText().trim();
            String word2 = word2TF.getText().trim();
            if(word1.equals("")){
                word1 = null;
            }
            if(word2.equals("")){
                word2 = null;
            }
            ShortestPath sp = new ShortestPath(this.graph);
            String result = sp.calcShortestPath(word1,word2);

            console.setText(result);
            // 按照换行符分割每一行
            String[] lines = result.split("\n");

            // 创建一个二维数组列表
            List<List<String>> twoDimensionalArray = new ArrayList<>();
            // 遍历每一行，按照'->'分割，并将结果添加到二维数组列表中
            for (String line : lines) {
                String[] parts = line.split("->");
                List<String> row = new ArrayList<>();
                for (String part : parts) {
                    row.add(part);
                }
                twoDimensionalArray.add(row);
            }

            GraphVisualizer printer = new GraphVisualizer(graph,twoDimensionalArray);
        });

        stackPane.getChildren().remove(prePane);
        stackPane.getChildren().add(pane);
    }


    @FXML
    protected void handleWalkButtonClicked(MouseEvent e) throws Exception {
        RandomWalk walker = new RandomWalk(this.graph, this.console);
        String randomWalkRes = walker.randomWalk();
        walker.writeRandomWalkPathToFile(randomWalkRes);
    }
}

