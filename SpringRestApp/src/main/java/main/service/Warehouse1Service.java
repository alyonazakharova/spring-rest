package main.service;

import main.entity.Good;
import main.entity.Warehouse1;

import java.util.List;

public interface Warehouse1Service {
    List<Warehouse1> listGoods();
    Good findGood(Integer id);
    Good addGood(Good good);
    void deleteFood(Integer id);
    Good updateGood(Integer id);
}
