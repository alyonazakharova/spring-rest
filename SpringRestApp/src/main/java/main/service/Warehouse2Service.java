package main.service;

import main.entity.Good;
import main.entity.Warehouse2;

import java.util.List;

public interface Warehouse2Service {
    List<Warehouse2> listGoods();
    Good findGood(Integer id);
    Good addGood(Good good);
    void deleteFood(Integer id);
    Good updateGood(Integer id);
}
