package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

    @FXML
    private TableView<SaleDto> salesTable;

    @FXML
    private TableColumn<SaleDto, Integer> salesIdColumn;

    @FXML
    private TableColumn<SaleDto, Integer> salesGoodIdColumn;

    @FXML
    private TableColumn<SaleDto, Integer> salesGoodCountColumn;

    @FXML
    private TableColumn<SaleDto, LocalDateTime> createDateColumn;

    @FXML
    private Button salesW1Btn;

    @FXML
    private Button salesW2Btn;

    @FXML
    private Label salesInfoLabel;

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

    public void getAllSales() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_SALES,
                HttpMethod.GET,
                null,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<SaleDto> sales = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                sales.add(new SaleDto((Integer)item.get("id"),
                        (Integer) item.get("goodId"),
                        (Integer) item.get("goodCount"),
                        LocalDateTime.parse((String) item.get("createDate"))));
            }
        }

        salesTable.setItems(FXCollections.observableList(sales));
    }

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

    public void getAllGoodsFromWarehouse2() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_WAREHOUSE_2,
                HttpMethod.GET,
                null,
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

//    public int findWarehouseIdByGoodId(String url, int goodId) {
//
//    }


    @FXML
    void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Good, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Good, Double>("priority"));

        getAllGoods();

        salesIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("id"));
        salesGoodIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodId"));
        salesGoodCountColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodCount"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, LocalDateTime>("createDate"));

        getAllSales();

        w1IdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("id"));
        w1GoodIdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("goodId"));
        w1GoodCountColumn.setCellValueFactory(new PropertyValueFactory<Warehouse1Dto, Integer>("goodCount"));

        getAllGoodsFromWarehouse1();

        w2IdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("id"));
        w2GoodIdColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("goodId"));
        w2GoodCountColumn.setCellValueFactory(new PropertyValueFactory<Warehouse2Dto, Integer>("goodCount"));

        getAllGoodsFromWarehouse2();

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

        salesW1Btn.setOnAction(actionEvent -> {
            SaleDto selected = salesTable.getSelectionModel().getSelectedItem();
            if (selected != null) {


            } else {
                salesInfoLabel.setText("An item must be selected");
            }
        });

        salesW2Btn.setOnAction(actionEvent -> {

        });

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

        w2SaveBtn.setOnAction(actionEvent -> {
            if (!w2GoodIdField.getText().isEmpty() && !w2GoodCountField.getText().isEmpty()) {
                int id, count;
                try {
                    id = Integer.parseInt(w2GoodIdField.getText());
                    count = Integer.parseInt(w2GoodCountField.getText());
                } catch (NumberFormatException e) {
                    w2InfoLabel.setText("Id and quantity must be integer");
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                Good good;
                try {
                    good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);
                } catch (Exception e) {
                    w2InfoLabel.setText("There is no good with this id");
                    return;
                }

                HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, count));
                restTemplate.postForEntity(URL_WAREHOUSE_2, request, Warehouse2.class);

                w2InfoLabel.setText("Added successfully");
                getAllGoodsFromWarehouse2();

                w2GoodIdField.setText("");
                w2GoodCountField.setText("");
            } else {
                w2InfoLabel.setText("Good id and quantity are required");
            }
        });

        w2UpdateBtn.setOnAction(actionEvent -> {
            w2InfoLabel.setText("");
            if (!w2GoodCountField.getText().isEmpty()) {
                Warehouse2Dto selected = w2Table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    int count;
                    try {
                        count = Integer.parseInt(w2GoodCountField.getText());
                    } catch (NumberFormatException e) {
                        w2InfoLabel.setText("Id and quantity must be integer");
                        return;
                    }

                    RestTemplate restTemplate = new RestTemplate();
                    int id = selected.getGoodId();
                    Good good = restTemplate.getForObject(URL_GOODS + "/" + id, Good.class);
                    HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, count));
                    restTemplate.exchange(URL_WAREHOUSE_2 + "/" + selected.getId(), HttpMethod.PUT, request, Warehouse2.class);

                    w2InfoLabel.setText("Updated successfully");
                    getAllGoodsFromWarehouse2();

                    w2GoodCountField.setText("");

                } else {
                    w2InfoLabel.setText("An item must be selected");
                }
            } else {
                w2InfoLabel.setText("Provide good quantity");
            }
        });

    }
}
