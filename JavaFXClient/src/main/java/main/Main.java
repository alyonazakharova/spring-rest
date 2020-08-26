package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream input = new FileInputStream(new File("src/main/resources/main.fxml"));
        Parent root = loader.load(input);

        stage.setScene(new Scene(root, 606, 392));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}