package main.web;

import main.entity.Sale;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.exception.GoodNotFoundException;
import main.service.Warehouse1Service;
import main.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping("/warehouse1")
    public ResponseEntity<List<Warehouse1>> getGoodsFromW1() {
        List<Warehouse1> list = warehouse1Service.listGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/warehouse1/{id}")
    public void deleteGoodFromW1(@PathVariable("id") int id, @RequestBody Warehouse1 w1Good) {
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
    public ResponseEntity<List<Warehouse2>> getGoodsFromW2() {
        List<Warehouse2> list = warehouse2Service.listGoods();
        return new ResponseEntity<>(list, HttpStatus.OK);
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
