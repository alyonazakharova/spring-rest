//package main.controller;
//
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javafx.collections.FXCollections;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.AnchorPane;
//import main.dto.*;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//public class MainController {
//
//    @FXML
//    private Tab goodsTab;
//
//    @FXML
//    private Tab salesTab;
//
//    @FXML
//    private Tab warehouse1Tab;
//
//    @FXML
//    private Tab warehouse2Tab;
//
//    @FXML
//    void initialize() {
//
//        FXMLLoader loader = new FXMLLoader();
//
//        try {
//            AnchorPane anchorPane1 = loader.load(getClass().getResource("src/main/resources/goods.fxml"));
//            goodsTab.setContent(anchorPane1);
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
//
//        loader = new FXMLLoader();
//        try {
//            AnchorPane anchorPane2 = loader.load(getClass().getResource("src/main/resources/sales.fxml"));
//            salesTab.setContent(anchorPane2);
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
//
//        loader = new FXMLLoader();
//        try {
//            AnchorPane anchorPane3 = loader.load(getClass().getResource("src/main/resources/warehouse1.fxml"));
//
//            warehouse1Tab.setContent(anchorPane3);
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
//
//        loader = new FXMLLoader();
//        try {
//            AnchorPane anchorPane4 = loader.load(getClass().getResource("src/main/resources/warehouse2.fxml"));
//            warehouse2Tab.setContent(anchorPane4);
//        } catch (IOException e) {
//            System.out.println("File not found");
//        }
//    }
//}



package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
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

    private String token = LoginController.jwtToken;

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
    private TextField quatityField;

    @FXML
    private Button createSaleBtn;

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

    public List<Good> getGoodsList() {
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
        return goods;
    }


    public void showAllGoods() {
        goodsTable.setItems(FXCollections.observableList(getGoodsList()));
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


    @FXML
    void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Good, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Good, Double>("priority"));

        showAllGoods();

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
            System.out.println("TOKEN: " + token);

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
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                HttpEntity<Good> request = new HttpEntity<>(new Good(name, priority), headers);

                try {
                    restTemplate.postForObject(URL_GOODS, request, Good.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    goodsInfoLabel.setText("Not autorized to perform this action");
                    return;
                }
            }
            goodsInfoLabel.setText("Good was successfully added");
            showAllGoods();

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
                    showAllGoods();

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
                showAllGoods();
            } else {
                goodsInfoLabel.setText("An item must be selected");
            }
        });

        createSaleBtn.setOnAction(actionEvent -> {
            Good selected = goodsTable.getSelectionModel().getSelectedItem();
            int quantity = 0;
            if (!quatityField.getText().isEmpty()){
                try {
                    quantity = Integer.parseInt(quatityField.getText());
                } catch (NumberFormatException e) {
                    goodsInfoLabel.setText("Quantity must be integer");
                    return;
                }
            } else {
                goodsInfoLabel.setText("Quantity must be specified");
                return;
            }
            if (selected != null) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", token);
                HttpEntity<Sale> request = new HttpEntity<>(new Sale(selected, quantity, LocalDateTime.now()), headers);
                try {
                    restTemplate.postForObject(URL_SALES, request, Sale.class);
                    goodsInfoLabel.setText("Sale was successfully created");
                    getAllSales();
                } catch (HttpClientErrorException.Forbidden e) {
                    goodsInfoLabel.setText("Not autorized to perform this action");
                    return;
                }
            } else {
                goodsInfoLabel.setText("An item must be selected");
            }
        });

        salesW1Btn.setOnAction(actionEvent -> {
            SaleDto selected = salesTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int saleId = selected.getId();
                int goodId = selected.getGoodId();
                int goodCount = selected.getGoodCount();

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

                int w1Id = 0;
                int currentGoodCount = 0;

                for (Warehouse1Dto item : goodsFromW1) {
                    if (item.getGoodId() == goodId) {
                        if (item.getGoodCount() < goodCount) {
                            salesInfoLabel.setText("Not enough goods in warehouse 1");
                            return;
                        }
                        w1Id = item.getId();
                        currentGoodCount = item.getGoodCount();
                        break;
                    }
                }

                if (w1Id == 0) {
                    salesInfoLabel.setText("There is no such good in warehouse 1");
                    return;
                }

                restTemplate = new RestTemplate();
                Good good = restTemplate.getForObject(URL_GOODS + "/" + goodId, Good.class);
                HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, currentGoodCount - goodCount));
                restTemplate.exchange(URL_WAREHOUSE_1 + "/" + w1Id, HttpMethod.PUT, request, Warehouse1.class);


                restTemplate = new RestTemplate();
                try {
                    restTemplate.delete(URL_SALES + "/" + saleId);
                } catch (Exception e) {
                    goodsInfoLabel.setText("This sale cannot be deleted");
                    return;
                }

                getAllSales();
                getAllGoodsFromWarehouse1();
                salesInfoLabel.setText("Successfully sent from warehouse 1");
            } else {
                salesInfoLabel.setText("An item must be selected");
            }
        });

        salesW2Btn.setOnAction(actionEvent -> {
            SaleDto selected = salesTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int saleId = selected.getId();
                int goodId = selected.getGoodId();
                int goodCount = selected.getGoodCount();

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

                int w2Id = 0;
                int currentGoodCount = 0;

                for (Warehouse2Dto item : goodsFromW2) {
                    if (item.getGoodId() == goodId) {
                        if (item.getGoodCount() < goodCount) {
                            salesInfoLabel.setText("Not enough goods in warehouse 2");
                            return;
                        }
                        w2Id = item.getId();
                        currentGoodCount = item.getGoodCount();
                        break;
                    }
                }

                if (w2Id == 0) {
                    salesInfoLabel.setText("There is no such good in warehouse 2");
                    return;
                }

                restTemplate = new RestTemplate();
                Good good = restTemplate.getForObject(URL_GOODS + "/" + goodId, Good.class);
                HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, currentGoodCount - goodCount));
                restTemplate.exchange(URL_WAREHOUSE_2 + "/" + w2Id, HttpMethod.PUT, request, Warehouse2.class);


                restTemplate = new RestTemplate();
                try {
                    restTemplate.delete(URL_SALES + "/" + saleId);
                } catch (Exception e) {
                    goodsInfoLabel.setText("This sale cannot be deleted");
                    return;
                }

                getAllSales();
                getAllGoodsFromWarehouse2();
                salesInfoLabel.setText("Successfully sent from warehouse 2");
            } else {
                salesInfoLabel.setText("An item must be selected");
            }
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
