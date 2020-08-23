package main.web;

import main.entity.Sale;
import main.exception.SaleNotFoundException;
import main.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private SaleService saleService;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> list = saleService.listSales();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Sale getSale(@PathVariable("id") int id) {
        try {
            return saleService.find(id);
        } catch (SaleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
    }

    @PostMapping
    public Sale addSale(@RequestBody Sale sale) {
        return saleService.add(sale);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable("id") int id) {
        try {
            saleService.delete(id);
        } catch (SaleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
    }

    @PutMapping("/{id}")
    public Sale updateSale(@PathVariable("id") int id, @RequestBody Sale sale) {
        try {
            return saleService.update(id, sale);
        } catch (SaleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
}
