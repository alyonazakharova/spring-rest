package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.model.Goods;
import main.model.Sales;
import main.model.Warehouse1;
import main.model.Warehouse2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_SALES = "http://localhost:8080/sales";
    private String URL_WAREHOUSE_1 = "http://localhost:8080/warehouse1";
    private String URL_WAREHOUSE_2 = "http://localhost:8080/warehouse2";


    @FXML
    private TableView<Goods> goodsTable;

    @FXML
    private TableColumn<Goods, Integer> idColumn;

    @FXML
    private TableColumn<Goods, String> nameColumn;

    @FXML
    private TableColumn<Goods, Double> priorityColumn;

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

    @FXML
    private TableView<Sales> salesTable;

    @FXML
    private TableColumn<Sales, Integer> salesIdColumn;

    @FXML
    private TableColumn<Sales, Goods> salesGoodIdColumn;

    @FXML
    private TableColumn<Sales, Integer> salesGoodCountColumn;

    @FXML
    private TableColumn<Sales, LocalDateTime> createDateColumn;

    @FXML
    private Button salesW1Btn;

    @FXML
    private Button salesW2Btn;

    @FXML
    private TableView<Warehouse1> w1Table;

    @FXML
    private TableColumn<Warehouse1, Integer> w1IdColumn;

    @FXML
    private TableColumn<Warehouse1, Integer> w1GoodIdColumn;

    @FXML
    private TableColumn<Warehouse1, CriteriaBuilder.In> w1GoodCountColumn;

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
    private TableView<Warehouse2> w2Table;

    @FXML
    private TableColumn<Warehouse2, Integer> w2IdColumn;

    @FXML
    private TableColumn<Warehouse2, Integer> w2GoodIdColumn;

    @FXML
    private TableColumn<Warehouse2, Integer> w2GoodCountColumn;

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


    public void getAllGoods() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_GOODS,
                HttpMethod.GET,
                null,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Goods> goods = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                goods.add(new Goods((Integer)item.get("id"),
                                    (String)item.get("name"),
                                    (Double)item.get("priority")));
            }
        }

        goodsTable.setItems(FXCollections.observableList(goods));
    }

    public void getAllSales() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_SALES,
                HttpMethod.GET,
                null,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Sales> sales = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                sales.add(new Sales((Integer)item.get("id"),
                        (Goods) item.get("goodId"),
                        (Integer) item.get("goodCount"),
                        (LocalDateTime)item.get("createDate")));
            }
        }

        salesTable.setItems(FXCollections.observableList(sales));
    }


    @FXML
    void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Goods, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Goods, String>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Goods, Double>("priority"));

        getAllGoods();

//        salesIdColumn.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("id"));
//        salesGoodIdColumn.setCellValueFactory(new PropertyValueFactory<Sales, Goods>("goodId"));
//        salesGoodCountColumn.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("goodCount"));
//        createDateColumn.setCellValueFactory(new PropertyValueFactory<Sales, LocalDateTime>("createDate"));
//
//        getAllSales();

        saveBtn.setOnAction(actionEvent -> {
            if (!nameField.getText().isEmpty() && !priorityColumn.getText().isEmpty()) {
                String name = nameField.getText();
                double priority = 0;
                try {
                    priority = Double.parseDouble(priorityField.getText());
                } catch (NumberFormatException e) {
                    goodsInfoLabel.setText("Priority type must be double");
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Goods> request = new HttpEntity<>(new Goods(name, priority));
                Goods g = restTemplate.postForObject(URL_GOODS, request, Goods.class);
            }
            goodsInfoLabel.setText("Good was successfully added");
            getAllGoods();
        });

        updateBtn.setOnAction(actionEvent -> {
            if (!nameField.getText().isEmpty() && !priorityField.getText().isEmpty()) {
                Goods selectedGood = goodsTable.getSelectionModel().getSelectedItem();
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
                    HttpEntity<Goods> request = new HttpEntity<>(new Goods(name, priority));
                    String url = URL_GOODS + "/" + selectedGood.getId();
                    restTemplate.exchange(url, HttpMethod.PUT, request, Goods.class);

                    goodsInfoLabel.setText("Item was successfully updated");
                    getAllGoods();
                } else {
                    goodsInfoLabel.setText("An item must be selected");
                }
            }
        });

        deleteBtn.setOnAction(actionEvent -> {
            Goods selectedGood = goodsTable.getSelectionModel().getSelectedItem();

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
