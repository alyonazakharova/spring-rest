package main.web;

import main.entity.Sale;
import main.exception.SaleNotFoundException;
import main.dto.SaleDto;
import main.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales")
public class SalesController {

    private SaleService saleService;

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDto>> getAllSales() {
        List<Sale> list = saleService.listSales();
        List<SaleDto> salesList = list.stream()
                .map(SaleDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(salesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public SaleDto getSale(@PathVariable("id") int id) {
        try {
            return new SaleDto(saleService.find(id));
        } catch (SaleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
    }

    @PostMapping
    public SaleDto addSale(@RequestBody Sale sale) {
        return new SaleDto(saleService.add(sale));
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
    public SaleDto updateSale(@PathVariable("id") int id, @RequestBody Sale sale) {
        try {
            return new SaleDto(saleService.update(id, sale));
        } catch (SaleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found");
        }
    }
}
