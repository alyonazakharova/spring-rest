package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesController {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_SALES = "http://localhost:8080/sales";
    private String URL_WAREHOUSE_1 = "http://localhost:8080/warehouse1";
    private String URL_WAREHOUSE_2 = "http://localhost:8080/warehouse2";

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
    private Button salesRefreshBtn;

    public void getAllSales() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = MainController.createHeaders();
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_SALES,
                HttpMethod.GET,
                request,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<SaleDto> sales = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                sales.add(new SaleDto(
                        (Integer)item.get("id"),
                        (Integer) item.get("goodId"),
                        (Integer) item.get("goodCount"),
                        LocalDateTime.parse((String) item.get("createDate"))));
            }
        }
        salesTable.setItems(FXCollections.observableList(sales));
    }

    @FXML
    void initialize() {

        salesIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("id"));
        salesGoodIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodId"));
        salesGoodCountColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodCount"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, LocalDateTime>("createDate"));

        getAllSales();

        HttpHeaders headers = MainController.createHeaders();
        HttpEntity simpleRequest = new HttpEntity<>(headers);

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
                        simpleRequest,
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
                ResponseEntity<Good> goodResponseEntity = restTemplate.exchange(
                        URL_GOODS + "/" + goodId,
                        HttpMethod.GET,
                        simpleRequest,
                        Good.class
                );
                Good good = goodResponseEntity.getBody();

                HttpEntity<Warehouse1> request = new HttpEntity<>(new Warehouse1(good, currentGoodCount - goodCount), headers);

                try {
                    restTemplate.exchange(URL_WAREHOUSE_1 + "/" + w1Id, HttpMethod.PUT, request, Warehouse1.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    salesInfoLabel.setText("Not autorized to perform this action");
                    return;
                }
                restTemplate = new RestTemplate();
                try {
                    restTemplate.exchange(URL_SALES + "/" + saleId, HttpMethod.DELETE, request, Void.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    salesInfoLabel.setText("Not autorized to perform this action");
                    return;
                } catch (Exception e) {
                    salesInfoLabel.setText("This sale cannot be deleted");
                    return;
                }

                getAllSales();
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
                        simpleRequest,
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

                ResponseEntity<Good> goodResponseEntity = restTemplate.exchange(
                        URL_GOODS + "/" + goodId,
                        HttpMethod.GET,
                        simpleRequest,
                        Good.class

                );
                Good good = goodResponseEntity.getBody();

                HttpEntity<Warehouse2> request = new HttpEntity<>(new Warehouse2(good, currentGoodCount - goodCount), headers);

                try {
                    restTemplate.exchange(URL_WAREHOUSE_2 + "/" + w2Id, HttpMethod.PUT, request, Warehouse2.class);
                } catch (HttpClientErrorException e) {
                    salesInfoLabel.setText("Not autorized to perform this action");
                    return;
                }

                restTemplate = new RestTemplate();
                try {
                    restTemplate.exchange(URL_SALES + "/" + saleId, HttpMethod.DELETE, request, Void.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    salesInfoLabel.setText("Not autorized to perform this action");
                    return;
                } catch (Exception e) {
                    salesInfoLabel.setText("This sale cannot be deleted");
                    return;
                }

                getAllSales();
                salesInfoLabel.setText("Successfully sent from warehouse 2");
            } else {
                salesInfoLabel.setText("An item must be selected");
            }
        });

        salesRefreshBtn.setOnAction(actionEvent -> {
            salesInfoLabel.setText("");
            getAllSales();
        });
    }
}
