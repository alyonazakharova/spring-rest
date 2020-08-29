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
import main.dto.Good;
import main.dto.Warehouse1;
import main.dto.Warehouse1Dto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Warehouse1Controller {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_WAREHOUSE_1 = "http://localhost:8080/warehouse1";

    private MainController mainController;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Warehouse1Dto> w1Table;

    @FXML
    private TableColumn<Warehouse1Dto, Integer> w1IdColumn;

    @FXML
    private TableColumn<Warehouse1Dto, Integer> w1GoodIdColumn;

    @FXML
    private TableColumn<Warehouse1Dto, Integer> w1GoodCountColumn;

    @FXML
    private TextField w1GoodIdField;

    @FXML
    private TextField w1GoodCountField;

    @FXML
    private Button w1SaveBtn;

    @FXML
    private Button w1UpdateBtn;

    @FXML
    private Button w1DeleteButton;

    @FXML
    private Label w1InfoLabel;

    public void getAllGoodsFromWarehouse1() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_WAREHOUSE_1,
                HttpMethod.GET,
                null,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Warehouse1Dto> goodsFromW1 = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                goodsFromW1.add(new Warehouse1Dto((Integer) item.get("id"),
                        (Integer) item.get("goodId"),
                        (Integer) item.get("goodCount")));
            }
        }

        w1Table.setItems(FXCollections.observableList(goodsFromW1));
    }

    @FXML
    void initialize() {

        w1IdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("id"));
        w1GoodIdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("goodId"));
        w1GoodCountColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("goodCount"));

        getAllGoodsFromWarehouse1();

        w1SaveBtn.setOnAction(actionEvent -> {
            if (!w1GoodIdField.getText().isEmpty() && !w1GoodCountField.getText().isEmpty()) {
                int id, count;
                try {
                    id = Integer.parseInt(w1GoodIdField.getText());
                    count = Integer.parseInt(w1GoodCountField.getText());
                } catch (NumberFormatException e) {
                    w1InfoLabel.setText("Id and quantity must be integer");
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                Good good;
                try {
                    good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);
                } catch (Exception e) {
                    w1InfoLabel.setText("There is no good with such id");
                    return;
                }

                HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, count));
                try {
                    restTemplate.postForEntity(URL_WAREHOUSE_1, request, Warehouse1.class);
                } catch (Exception e) {
                    w1InfoLabel.setText("There is already an item with such good_id");
                    return;
                }


                w1InfoLabel.setText("Added successfully");
                getAllGoodsFromWarehouse1();

                w1GoodIdField.setText("");
                w1GoodCountField.setText("");
            } else {
                w1InfoLabel.setText("Good id and quantity are required");
            }
        });

        w1UpdateBtn.setOnAction(actionEvent -> {
            if (!w1GoodCountField.getText().isEmpty()) {
                Warehouse1Dto selected = w1Table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    int count;
                    try {
                        count = Integer.parseInt(w1GoodCountField.getText());
                    } catch (NumberFormatException e) {
                        w1InfoLabel.setText("Id and quantity must be integer");
                        return;
                    }

                    RestTemplate restTemplate = new RestTemplate();
                    int id = selected.getGoodId();
                    Good good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);
                    HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, count));
                    restTemplate.exchange(URL_WAREHOUSE_1 + "/" + selected.getId(), HttpMethod.PUT, request, Warehouse1.class);

                    w1InfoLabel.setText("Updated successfully");
                    getAllGoodsFromWarehouse1();

                    w1GoodCountField.setText("");

                } else {
                    w1InfoLabel.setText("An item must be selected");
                }
            } else {
                w1InfoLabel.setText("Provide good quantity");
            }
        });
    }
}
