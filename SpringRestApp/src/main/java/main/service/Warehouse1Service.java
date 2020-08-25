package main.service;

import main.entity.Good;
import main.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {
    List<Warehouse1> listGoods();
    Warehouse1 find(Integer id);
    Warehouse1 addGood(Warehouse1 w1Good);
    Warehouse1 updateGood(Integer id, Warehouse1 w1Good);
    void deleteGood(Integer id);
}
