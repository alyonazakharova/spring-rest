package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    public static String token = LoginController.jwtToken;

    static org.springframework.http.HttpHeaders createHeaders() {
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        return headers;
    }

    static String FORBIDDEN_MSG = "You do not have permission to perform this action";
    static String UNAUTORIZED_MSG = "Session time is over.\nPlease, re-login.";

    static void showInfo(String message,
                         Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText(message);
        textArea.setEditable(false);
        VBox vBox = new VBox();
        vBox.getChildren().add(textArea);
        alert.getDialogPane().setMaxWidth(300);
        alert.getDialogPane().setMaxHeight(150);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    @FXML
    private Tab goodsTab;

    @FXML
    private Tab salesTab;

    @FXML
    private Tab warehouse1Tab;

    @FXML
    private Tab warehouse2Tab;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        FXMLLoader loader = new FXMLLoader();

        try {
            AnchorPane ap1 = loader.load(getClass().getResource("/goods.fxml"));
            goodsTab.setContent(ap1);
        } catch (IOException e) {
            System.out.println("File not found");
        }

        loader = new FXMLLoader();
        try {
            AnchorPane ap2 = loader.load(getClass().getResource("/sales.fxml"));
            salesTab.setContent(ap2);
        } catch (IOException e) {
            System.out.println("File not found");
        }

        loader = new FXMLLoader();
        try {
            AnchorPane ap3 = loader.load(getClass().getResource("/warehouse1.fxml"));
            warehouse1Tab.setContent(ap3);
        } catch (IOException e) {
            System.out.println("File not found");
        }

        loader = new FXMLLoader();
        try {
            AnchorPane ap4 = loader.load(getClass().getResource("/warehouse2.fxml"));
            warehouse2Tab.setContent(ap4);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
