package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.dto.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    private String URL_LOGIN = "http://localhost:8080/auth/signin";

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Button loginBtn;

    public String login(String username, String password) {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> map = new HashMap<>();
        map.put("userName", username);
        map.put("password", password);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map);
        try {
            ResponseEntity<Token> responseEntity = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, entity, Token.class);
            String token = responseEntity.getBody().getToken();
            return "Bearer " + token;
        } catch (Exception e) {
            System.out.println("oops shit");
        }

        return "return";
    }

    @FXML
    void initialize() {
        loginBtn.setOnAction(actionEvent -> {
            if (!loginField.getText().isEmpty() && !pwdField.getText().isEmpty()) {
                System.out.println(login(loginField.getText(), pwdField.getText()));

                FXMLLoader loader = new FXMLLoader();
                try {
                    FileInputStream input = new FileInputStream(new File("src/main/resources/main.fxml"));
                    Parent root = loader.load(input);

                    Stage stage = new Stage();

                    stage.setScene(new Scene(root, 606, 392));
                    stage.show();
                } catch (Exception e) {

                }
            }


        });
    }
}
