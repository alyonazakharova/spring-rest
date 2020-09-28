package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.Good;
import main.dto.Warehouse1;
import main.dto.Warehouse1Dto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Warehouse1Controller {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_WAREHOUSE_1 = "http://localhost:8080/warehouse1";

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
    private Button w1RefreshBtn;

    public void getAllGoodsFromWarehouse1() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = MainController.createHeaders();
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<Object> response = null;
        try {
            response = restTemplate.exchange(
                    URL_WAREHOUSE_1,
                    HttpMethod.GET,
                    request,
                    Object.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
            return;
        }

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

        HttpHeaders headers = MainController.createHeaders();
        HttpEntity simpleRequest = new HttpEntity<>(headers);

        w1SaveBtn.setOnAction(actionEvent -> {
            String goodId = w1GoodIdField.getText();
            String goodCount = w1GoodCountField.getText();
            w1GoodIdField.setText("");
            w1GoodCountField.setText("");
            if (!goodId.isEmpty() && !goodCount.isEmpty()) {
                int id, count;
                try {
                    id = Integer.parseInt(goodId);
                    count = Integer.parseInt(goodCount);
                } catch (NumberFormatException e) {
                    MainController.showInfo("Id and quantity must be integer", Alert.AlertType.WARNING);
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                Good good;
                try {
                    ResponseEntity<Good> goodResponseEntity = restTemplate.exchange(
                            URL_GOODS + "/" + id,
                            HttpMethod.GET,
                            simpleRequest,
                            Good.class);
                    good = goodResponseEntity.getBody();
                } catch (HttpClientErrorException.Unauthorized e) {
                    MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
                    return;
                } catch (Exception e) {
                    MainController.showInfo("There is no good with such id", Alert.AlertType.ERROR);
                    return;
                }

                HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, count), headers);
                try {
                    restTemplate.postForEntity(URL_WAREHOUSE_1, request, Warehouse1.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    MainController.showInfo(MainController.FORBIDDEN_MSG, Alert.AlertType.ERROR);
                    w1GoodIdField.setText("");
                    w1GoodCountField.setText("");
                    return;
                } catch (HttpClientErrorException.Unauthorized e) {
                    MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
                    return;
                } catch (Exception e) {
                    MainController.showInfo("There is already an item with such good_id", Alert.AlertType.ERROR);
                    return;
                }

                getAllGoodsFromWarehouse1();
                MainController.showInfo("Added successfully", Alert.AlertType.CONFIRMATION);
                w1GoodIdField.setText("");
                w1GoodCountField.setText("");
            } else {
                MainController.showInfo("Good id and quantity are required", Alert.AlertType.WARNING);
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
                        MainController.showInfo("Quantity must be integer", Alert.AlertType.WARNING);
                        w1GoodCountField.setText("");
                        return;
                    }

                    RestTemplate restTemplate = new RestTemplate();
                    int id = selected.getGoodId();

                    ResponseEntity<Good> goodResponseEntity = null;
                    try {
                        goodResponseEntity = restTemplate.exchange(
                                URL_GOODS + "/" + id,
                                HttpMethod.GET,
                                simpleRequest,
                                Good.class);
                    } catch (HttpClientErrorException.Unauthorized e) {
                        MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
                        return;
                    }
                    Good good = goodResponseEntity.getBody();

                    HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, count), headers);

                    try {
                        restTemplate.exchange(URL_WAREHOUSE_1 + "/" + selected.getId(), HttpMethod.PUT, request, Warehouse1.class);
                    } catch (HttpClientErrorException.Forbidden e) {
                        MainController.showInfo(MainController.FORBIDDEN_MSG, Alert.AlertType.ERROR);
                        w1GoodCountField.setText("");
                        return;
                    } catch (HttpClientErrorException.Unauthorized e) {
                        MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
                        return;
                    }

                    getAllGoodsFromWarehouse1();
                    MainController.showInfo("Updated successfully", Alert.AlertType.INFORMATION);
                    w1GoodCountField.setText("");
                } else {
                    MainController.showInfo("An item must be selected", Alert.AlertType.WARNING);
                }
            } else {
                MainController.showInfo("Quantity is required", Alert.AlertType.WARNING);
            }
        });

        w1RefreshBtn.setOnAction(actionEvent -> {
            getAllGoodsFromWarehouse1();
        });

        w1DeleteButton.setOnAction(actionEvent -> {
            Warehouse1Dto selected = w1Table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                RestTemplate restTemplate = new RestTemplate();
                try {
                    restTemplate.exchange(URL_WAREHOUSE_1 + "/" + selected.getId(), HttpMethod.DELETE, simpleRequest, Void.class);
                    getAllGoodsFromWarehouse1();
                    MainController.showInfo("Item was successfully deleted", Alert.AlertType.INFORMATION);
                } catch (HttpClientErrorException.Forbidden e) {
                    MainController.showInfo(MainController.FORBIDDEN_MSG, Alert.AlertType.ERROR);
                } catch (HttpClientErrorException.Unauthorized e) {
                    MainController.showInfo(MainController.UNAUTORIZED_MSG, Alert.AlertType.ERROR);
                }
            } else {
                MainController.showInfo("An item must be selected", Alert.AlertType.WARNING);
            }
        });
    }
}
