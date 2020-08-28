package main.web;

import main.entity.Good;
import main.exception.GoodNotFoundException;
import main.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private GoodService goodService;

    @Autowired
    public void setGoodService(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping
    public List<Good> getAllGoods() {
        return goodService.list();
    }

    @GetMapping("/{id}")
    public Good getGood(@PathVariable("id") int id) {
        try {
            return goodService.find(id);
        } catch (GoodNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found");
        }
    }

    @PostMapping
    public Good addGood(@RequestBody Good good) {
        return goodService.add(good);
    }

    @DeleteMapping("/{id}")
    public void deleteGood(@PathVariable("id") int id) {
        try {
            Good good = goodService.find(id);
            goodService.delete(id);
        } catch (GoodNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found");
        }
    }

    @PutMapping("/{id}")
    public Good updateGood(@PathVariable("id") int id, @RequestBody Good good) {
        try {
            return goodService.update(id, good);
        } catch (GoodNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Good not found");
        }
    }
}
