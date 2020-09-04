package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.dto.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    private String URL_LOGIN = "http://localhost:8080/auth/signin";

    public static String jwtToken;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private Label loginInfoLabel;

    public String login(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("userName", username);
        map.put("password", password);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map);
        try {
            ResponseEntity<Token> responseEntity = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, entity, Token.class);
            String token = responseEntity.getBody().getToken();
            jwtToken = "Bearer " + token;
            return jwtToken;
        } catch (Exception e) {
            System.out.println("oopsie");
        }
        return "error";
    }

    @FXML
    void initialize() {

        loginBtn.setOnAction(actionEvent -> {
            if (!loginField.getText().isEmpty() && !pwdField.getText().isEmpty()) {
                if (login(loginField.getText(), pwdField.getText()) != "error") {
                    loginBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/main.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    loginInfoLabel.setText("Invalid login or/and password");
                }
            }
        });


        signUpBtn.setOnAction(actionEvent -> {
            signUpBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/registration.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("oopsie");
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
