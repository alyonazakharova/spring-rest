package main.service;

import main.entity.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> listSales();
    Sale find(Integer id);
    Sale add(Sale sale);
    void delete(Integer id);
    Sale update(Integer id, Sale sale);
}
