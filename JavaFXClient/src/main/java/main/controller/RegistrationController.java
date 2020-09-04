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
import main.dto.Good;
import main.dto.User;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

public class RegistrationController {

    private String URL_REGISTRATION = "http://localhost:8080/auth/signup";

    @FXML
    private TextField signUpLoginField;

    @FXML
    private PasswordField signUpPwd1Field;

    @FXML
    private PasswordField signUpPwd2Field;

    @FXML
    private Label signUpInfoLabel;

    @FXML
    private Button registerBtn;

    @FXML
    void initialize() {

        registerBtn.setOnAction(actionEvent -> {
            String login = signUpLoginField.getText();
            String pwd1 = signUpPwd1Field.getText();
            String pwd2 = signUpPwd2Field.getText();

            if (!login.isEmpty() && !pwd1.isEmpty() && !pwd2.isEmpty()) {
                if (pwd1.equals(pwd2)) {
                    RestTemplate restTemplate = new RestTemplate();
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    JSONObject userJson = new JSONObject();
                    userJson.put("username", login);
                    userJson.put("password", pwd1);
                    userJson.put("roles", Collections.singletonList("ROLE_USER"));
                    HttpEntity<String> request = new HttpEntity<>(userJson.toString(), headers);
                    try {
                        restTemplate.postForEntity(URL_REGISTRATION, request, String.class);
                    } catch (Exception e) {
                        signUpInfoLabel.setText("User with this username already exists ");
                        signUpLoginField.setText("");
                        signUpPwd1Field.setText("");
                        signUpPwd2Field.setText("");
                        return;
                    }

                    registerBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/login.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        System.out.println("oopsie");
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } else {
                    signUpInfoLabel.setText("Passwords do not match");
                }
            }
        });

    }
}
