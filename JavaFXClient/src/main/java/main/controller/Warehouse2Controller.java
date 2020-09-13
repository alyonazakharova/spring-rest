package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.Good;
import main.dto.Warehouse1Dto;
import main.dto.Warehouse2;
import main.dto.Warehouse2Dto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Warehouse2Controller {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_WAREHOUSE_2 = "http://localhost:8080/warehouse2";

    @FXML
    private TableView<Warehouse2Dto> w2Table;

    @FXML
    private TableColumn<Warehouse2Dto, Integer> w2IdColumn;

    @FXML
    private TableColumn<Warehouse2Dto, Integer> w2GoodIdColumn;

    @FXML
    private TableColumn<Warehouse2Dto, Integer> w2GoodCountColumn;

    @FXML
    private TextField w2GoodIdField;

    @FXML
    private TextField w2GoodCountField;

    @FXML
    private Button w2SaveBtn;

    @FXML
    private Button w2UpdateBtn;

    @FXML
    private Button w2DeleteBtn;

    @FXML
    private Label w2InfoLabel;

    @FXML
    private Button w2RefreshBtn;

    public void getAllGoodsFromWarehouse2() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = MainController.createHeaders();
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_WAREHOUSE_2,
                HttpMethod.GET,
                request,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Warehouse2Dto> goodsFromW2 = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                goodsFromW2.add(new Warehouse2Dto((Integer) item.get("id"),
                        (Integer) item.get("goodId"),
                        (Integer) item.get("goodCount")));
            }
        }

        w2Table.setItems(FXCollections.observableList(goodsFromW2));
    }

    @FXML
    void initialize() {

        w2IdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("id"));
        w2GoodIdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("goodId"));
        w2GoodCountColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("goodCount"));

        getAllGoodsFromWarehouse2();

        HttpHeaders headers = MainController.createHeaders();
        HttpEntity simpleRequest = new HttpEntity<>(headers);

        w2SaveBtn.setOnAction(actionEvent -> {
            if (!w2GoodIdField.getText().isEmpty() && !w2GoodCountField.getText().isEmpty()) {
                int id, count;
                try {
                    id = Integer.parseInt(w2GoodIdField.getText());
                    count = Integer.parseInt(w2GoodCountField.getText());
                } catch (NumberFormatException e) {
                    w2InfoLabel.setText("Id and quantity must be integer");
                    w2GoodIdField.setText("");
                    w2GoodCountField.setText("");
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                Good good;
                try {
                    ResponseEntity<Good> goodResponseEntity = restTemplate.exchange(
                            URL_GOODS + "/" + id,
                            HttpMethod.GET,
                            simpleRequest,
                            Good.class
                    );
                    good = goodResponseEntity.getBody();
//                    good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);
                } catch (Exception e) {
                    w2InfoLabel.setText("There is no good with this id");
                    w2GoodIdField.setText("");
                    w2GoodCountField.setText("");
                    return;
                }

                HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, count), headers);
                try {
                    restTemplate.postForEntity(URL_WAREHOUSE_2, request, Warehouse2.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    w2InfoLabel.setText("Not autorized to perform this action");
                    w2GoodIdField.setText("");
                    w2GoodCountField.setText("");
                    return;
                }

                getAllGoodsFromWarehouse2();
                w2InfoLabel.setText("Added successfully");
                w2GoodIdField.setText("");
                w2GoodCountField.setText("");
            } else {
                w2InfoLabel.setText("Good id and quantity are required");
            }
        });

        w2UpdateBtn.setOnAction(actionEvent -> {
            if (!w2GoodCountField.getText().isEmpty()) {
                Warehouse2Dto selected = w2Table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    int count;
                    try {
                        count = Integer.parseInt(w2GoodCountField.getText());
                    } catch (NumberFormatException e) {
                        w2InfoLabel.setText("Quantity must be integer");
                        w2GoodCountField.setText("");
                        return;
                    }

                    RestTemplate restTemplate = new RestTemplate();
                    int id = selected.getGoodId();

                    ResponseEntity<Good> goodResponseEntity = restTemplate.exchange(
                            URL_GOODS + "/" + id,
                            HttpMethod.GET,
                            simpleRequest,
                            Good.class
                    );
                    Good good = goodResponseEntity.getBody();
//                    Good good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);

                    HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, count), headers);
                    try {
                        restTemplate.exchange(URL_WAREHOUSE_2 + "/" + selected.getId(), HttpMethod.PUT, request, Warehouse2.class);
                    } catch (HttpClientErrorException.Forbidden e) {
                        w2InfoLabel.setText("Not autorized to perform this action");
                        w2GoodCountField.setText("");
                        return;
                    }

                    getAllGoodsFromWarehouse2();
                    w2InfoLabel.setText("Updated successfully");
                    w2GoodCountField.setText("");

                } else {
                    w2InfoLabel.setText("An item must be selected");
                }
            } else {
                w2InfoLabel.setText("Provide good quantity");
            }
        });

        w2RefreshBtn.setOnAction(actionEvent -> {
            w2InfoLabel.setText("");
            getAllGoodsFromWarehouse2();
        });

        w2DeleteBtn.setOnAction(actionEvent -> {
            Warehouse2Dto selected = w2Table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                RestTemplate restTemplate = new RestTemplate();
                try {
                    restTemplate.exchange(URL_WAREHOUSE_2 + "/" + selected.getId(), HttpMethod.DELETE, simpleRequest, Void.class);
                    getAllGoodsFromWarehouse2();
                    w2InfoLabel.setText("Item was successfully deleted");
                } catch (HttpClientErrorException.Forbidden e) {
                    w2InfoLabel.setText("Not autorized to perform this action");
                }
            } else {
                w2InfoLabel.setText("An item must be selected");
            }
        });
    }
}
