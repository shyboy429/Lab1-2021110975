package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Begin of the program.
 */
public class MainApplication extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("BaseWindow.fxml"));
    Parent root;
    root = loader.load();

    //获取主窗口上的控件对象
    Button textBt;
    textBt = (Button) loader.getNamespace().get("textButton");
    Button showBt;
    showBt = (Button) loader.getNamespace().get("showButton");
    Button queryBt;
    queryBt = (Button) loader.getNamespace().get("queryButton");
    Button generateBt;
    generateBt = (Button) loader.getNamespace().get("generateButton");
    Button pathBt;
    pathBt = (Button) loader.getNamespace().get("pathButton");
    Button walkBt;
    walkBt = (Button) loader.getNamespace().get("walkButton");
    //将暂时不可用的控件设置为不可用
    textBt.setDisable(true);
    showBt.setDisable(true);
    queryBt.setDisable(true);
    generateBt.setDisable(true);
    pathBt.setDisable(true);
    walkBt.setDisable(true);
    Scene scene = new Scene(root);
    stage.setTitle("软件工程实验一");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
