package main.service.impl;

import main.entity.Good;
import main.entity.Warehouse2;
import main.repository.Warehouse2Repository;
import main.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Warehouse2ServiceImpl implements Warehouse2Service {

    @Autowired
    private Warehouse2Repository warehouse2Repository;

    @Override
    public List<Warehouse2> listGoods() {
        return (List<Warehouse2>) warehouse2Repository.findAll();
    }

    @Override
    public Good findGood(Integer id) {
        return null;
    }

    @Override
    public Good addGood(Good good) {
        return null;
    }

    @Override
    public void deleteFood(Integer id) {

    }

    @Override
    public Good updateGood(Integer id) {
        return null;
    }
}
