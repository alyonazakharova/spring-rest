package main.service;

import main.entity.Good;
import main.entity.Warehouse2;

import java.util.List;

public interface Warehouse2Service {
    List<Warehouse2> listGoods();
    Warehouse2 addGood(Warehouse2 w2Good);
    Warehouse2 updateGood(Integer id, Warehouse2 w2Good);
    void deleteFood(Integer id);
}
