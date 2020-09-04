package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Tab goodsTab;

    @FXML
    private Tab salesTab;

    @FXML
    private Tab warehouse1Tab;

    @FXML
    private Tab warehouse2Tab;

    @Override
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
