package main.controller;

import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.cell.PropertyValueFactory;
import main.dto.SaleDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SalesController {

    private String URL_SALES = "http://localhost:8080/sales";

    private MainController mainController;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    void initialize() {

        salesIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("id"));
        salesGoodIdColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodId"));
        salesGoodCountColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, Integer>("goodCount"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<SaleDto, LocalDateTime>("createDate"));

        getAllSales();

        salesW1Btn.setOnAction(actionEvent -> {
            SaleDto selected = salesTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                //...
            } else {
                salesInfoLabel.setText("An item must be selected");
            }
        });

        salesW2Btn.setOnAction(actionEvent -> {
            //...
        });
    }
}
