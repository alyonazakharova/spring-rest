package main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.dto.Good;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GoodsController {

    private static String URL_GOODS = "http://localhost:8080/goods";

    private MainController mainController;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Good> goodsTable;

    @FXML
    private TableColumn<Good, Integer> idColumn;

    @FXML
    private TableColumn<Good, String> nameColumn;

    @FXML
    private TableColumn<Good, Double> priorityColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priorityField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Label goodsInfoLabel;

    public void getAllGoods() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_GOODS,
                HttpMethod.GET,
                null,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Good> goods = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                goods.add(new Good((Integer)item.get("id"),
                        (String)item.get("name"),
                        (Double)item.get("priority")));
            }
        }

        goodsTable.setItems(FXCollections.observableList(goods));
    }

    @FXML
    void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Good, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Good, Double>("priority"));

        getAllGoods();

        saveBtn.setOnAction(actionEvent -> {
            if (!nameField.getText().isEmpty() && !priorityField.getText().isEmpty()) {
                String name = nameField.getText();
                double priority = 0;
                try {
                    priority = Double.parseDouble(priorityField.getText());
                } catch (NumberFormatException e) {
                    goodsInfoLabel.setText("Priority type must be double");
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Good> request = new HttpEntity<>(new Good(name, priority));
                Good g = restTemplate.postForObject(URL_GOODS, request, Good.class);
            }
            goodsInfoLabel.setText("Good was successfully added");
            getAllGoods();

            nameField.setText("");
            priorityField.setText("");
        });

        updateBtn.setOnAction(actionEvent -> {
            if (!nameField.getText().isEmpty() && !priorityField.getText().isEmpty()) {
                Good selectedGood = goodsTable.getSelectionModel().getSelectedItem();
                if (selectedGood != null) {

                    String name = nameField.getText();
                    double priority = 0;
                    try {
                        priority = Double.parseDouble(priorityField.getText());
                    } catch (NumberFormatException e) {
                        goodsInfoLabel.setText("Priority type must be double");
                        return;
                    }

                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity<Good> request = new HttpEntity<>(new Good(name, priority));
                    String url = URL_GOODS + "/" + selectedGood.getId();
                    restTemplate.exchange(url, HttpMethod.PUT, request, Good.class);

                    goodsInfoLabel.setText("Item was successfully updated");
                    getAllGoods();

                    nameField.setText("");
                    priorityField.setText("");
                } else {
                    goodsInfoLabel.setText("An item must be selected");
                }
            }
        });

        deleteBtn.setOnAction(actionEvent -> {
            Good selectedGood = goodsTable.getSelectionModel().getSelectedItem();

            if (selectedGood != null) {
                RestTemplate restTemplate = new RestTemplate();
                String url = URL_GOODS + "/" + selectedGood.getId();
                try {
                    restTemplate.delete(url);
                } catch (Exception e) {
                    goodsInfoLabel.setText("This good cannot be deleted");
                    return;
                }

                goodsInfoLabel.setText("Item was successfully deleted");
                getAllGoods();
            } else {
                goodsInfoLabel.setText("An item must be selected");
            }
        });
    }
}
