package main.web;

import main.dto.Warehouse1Dto;
import main.dto.Warehouse2Dto;
import main.entity.Good;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.exception.GoodNotFoundException;
import main.service.GoodService;
import main.service.Warehouse1Service;
import main.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WarehouseController {

    private Warehouse1Service warehouse1Service;
    private Warehouse2Service warehouse2Service;

    @Autowired
    public void setWarehouse1Service(Warehouse1Service warehouse1Service) {
        this.warehouse1Service = warehouse1Service;
    }

    @Autowired
    public void setWarehouse2Service(Warehouse2Service warehouse2Service) {
        this.warehouse2Service = warehouse2Service;
    }

    private GoodService goodService;

    @Autowired
    public void setGoodService(GoodService goodService) {
        this.goodService = goodService;
    }


    @GetMapping("/warehouse1")
    public ResponseEntity<List<Warehouse1Dto>> getGoodsFromW1() {
        List<Warehouse1> list = warehouse1Service.listGoods();
        List<Warehouse1Dto> w1List = list.stream()
                .map(Warehouse1Dto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(w1List, HttpStatus.OK);
    }

    @DeleteMapping("/warehouse1/{id}")
    public void deleteGoodFromW1(@PathVariable("id") int id) {
        try {
            warehouse1Service.deleteGood(id);
        } catch (GoodNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found in warehouse 1");
        }
    }

    @PostMapping("/warehouse1")
    public Warehouse1 addGoodToW1(@RequestBody Warehouse1 w1Good) {
        return warehouse1Service.addGood(w1Good);
    }

    @PutMapping("/warehouse1/{id}")
    public Warehouse1 updateGoodInW1(@PathVariable("id") int id, @RequestBody Warehouse1 w1Good) {
        try {
            return warehouse1Service.updateGood(id, w1Good);
        } catch (GoodNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found in warehouse 1");
        }
    }



    @GetMapping("/warehouse2")
    public ResponseEntity<List<Warehouse2Dto>> getGoodsFromW2() {
        List<Warehouse2> list = warehouse2Service.listGoods();
        List<Warehouse2Dto> w2List = list.stream()
                .map(Warehouse2Dto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(w2List, HttpStatus.OK);
    }

    @PostMapping("/warehouse2")
    public Warehouse2 addGoodToW2(@RequestBody Warehouse2 w2Good) {
        return warehouse2Service.addGood(w2Good);
    }

    @PutMapping("/warehouse2/{id}")
    public Warehouse2 updateGoodInW2(@PathVariable("id") int id, @RequestBody Warehouse2 w2Good) {
        try {
            return warehouse2Service.updateGood(id, w2Good);
        } catch (GoodNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found in warehouse 2");
        }
    }

    @DeleteMapping("/warehouse2/{id}")
    public void deleteGoodFromW2(@PathVariable("id") int id) {
        try {
            warehouse2Service.deleteGood(id);
        } catch (GoodNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found in warehouse 2");
        }
    }
}
