package main.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import main.dto.Good;
import main.dto.Sale;
import main.exception.DataNotSpecifiedException;
import main.exception.InvalidTypeException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class GoodsController {

    private static String URL_GOODS = "http://localhost:8080/goods";
    private String URL_SALES = "http://localhost:8080/sales";

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
    private TextField quantityField;

    @FXML
    private Button createSaleBtn;


    public List<Good> getGoodsList() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = MainController.createHeaders();
        HttpEntity request = new HttpEntity<>(headers);
        ResponseEntity<Object> response = restTemplate.exchange(
                URL_GOODS,
                HttpMethod.GET,
                request,
                Object.class);

        List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody();

        List<Good> goods = new ArrayList<>();
        if (result != null) {
            for (Map<String, Object> item : result) {
                goods.add(new Good((Integer) item.get("id"),
                        (String) item.get("name"),
                        (Double) item.get("priority")));
            }
        }
        return goods;
    }

    public void showAllGoods() {
        goodsTable.setItems(FXCollections.observableList(getGoodsList()));
    }

    private Pair<String, Double> getNameAndPriority() {
        String name = nameField.getText();
        String priorityStr = priorityField.getText();

        if (!name.isEmpty() && !priorityStr.isEmpty()) {
            Double priority;
            try {
                priority = Double.parseDouble(priorityStr);
            } catch (NumberFormatException e) {
                throw new InvalidTypeException("Priority must me double");
            }
            return new Pair<>(name, priority);
        } else {
            throw new DataNotSpecifiedException("Name and priority must be specified");
        }
    }

    @FXML
    void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<Good, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Good, String>("name"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Good, Double>("priority"));
        showAllGoods();

        saveBtn.setOnAction(actionEvent -> {
            Pair<String, Double> nameAndPriority;
            try {
                nameAndPriority = getNameAndPriority();
            } catch (InvalidTypeException | DataNotSpecifiedException e) {
                goodsInfoLabel.setText(e.getMessage());
                nameField.setText("");
                priorityField.setText("");
                return;
            }

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = MainController.createHeaders();
            HttpEntity<Good> request = new HttpEntity<>(new Good(nameAndPriority.getKey(), nameAndPriority.getValue()), headers);
            try {
                restTemplate.postForObject(URL_GOODS, request, Good.class);
            } catch (HttpClientErrorException.Forbidden e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, "You do not have permisson to perform this action");
                goodsInfoLabel.setText("Not autorized to perform this action");
                nameField.setText("");
                priorityField.setText("");
                return;
            }
            goodsInfoLabel.setText("Good was successfully added");
            nameField.setText("");
            priorityField.setText("");
            showAllGoods();
        });


        updateBtn.setOnAction(actionEvent -> {
            Good selectedGood = goodsTable.getSelectionModel().getSelectedItem();
            if (selectedGood != null) {
                Pair<String, Double> nameAndPriority;
                try {
                    nameAndPriority = getNameAndPriority();
                } catch (InvalidTypeException | DataNotSpecifiedException e) {
                    goodsInfoLabel.setText(e.getMessage());
                    return;
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = MainController.createHeaders();
                HttpEntity<Good> request = new HttpEntity<>(new Good(nameAndPriority.getKey(),nameAndPriority.getValue()), headers);
                try {
                    restTemplate.exchange(URL_GOODS + "/" + selectedGood.getId(),HttpMethod.PUT, request, Good.class);
                } catch (HttpClientErrorException.Forbidden e) {
                    goodsInfoLabel.setText("Not autorized to perform this action");
                    nameField.setText("");
                    priorityField.setText("");
                    return;
                }
                goodsInfoLabel.setText("Item was successfully updated");
                nameField.setText("");
                priorityField.setText("");
                showAllGoods();
            }
        });

        deleteBtn.setOnAction(actionEvent -> {
            Good selectedGood = goodsTable.getSelectionModel().getSelectedItem();
            if (selectedGood != null) {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = MainController.createHeaders();
                HttpEntity request = new HttpEntity(headers);
                try {
                    restTemplate.exchange(URL_GOODS + "/" + selectedGood.getId(), HttpMethod.DELETE, request, Void.class);
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
            if (!quantityField.getText().isEmpty()){
                try {
                    quantity = Integer.parseInt(quantityField.getText());
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
                HttpHeaders headers = MainController.createHeaders();
                HttpEntity<Sale> request = new HttpEntity<>(new Sale(selected, quantity, LocalDateTime.now()), headers);
                try {
                    restTemplate.postForObject(URL_SALES, request, Sale.class);
                    goodsInfoLabel.setText("Sale was successfully created");
                } catch (HttpClientErrorException.Forbidden e) {
                    goodsInfoLabel.setText("Not autorized to perform this action");
                    return;
                }
            } else {
                goodsInfoLabel.setText("An item must be selected");
            }
        });
    }
}
