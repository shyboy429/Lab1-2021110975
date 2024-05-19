package ui;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.image.Image;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BaseWindow.fxml"));
        Parent root = loader.load();

        //获取主窗口上的控件对象
        Button textBT =  (Button)loader.getNamespace().get("textButton");
        Button showBT = (Button)loader.getNamespace().get("showButton");
        Button queryBT = (Button)loader.getNamespace().get("queryButton");
        Button generateBT = (Button)loader.getNamespace().get("generateButton");
        Button pathBT = (Button)loader.getNamespace().get("pathButton");
        Button walkBT = (Button)loader.getNamespace().get("walkButton");
        //将暂时不可用的控件设置为不可用
        textBT.setDisable(true);
        showBT.setDisable(true);
        queryBT.setDisable(true);
        generateBT.setDisable(true);
        pathBT.setDisable(true);
        walkBT.setDisable(true);
        Scene scene = new Scene(root);
        stage.setTitle("软件工程实验一");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
