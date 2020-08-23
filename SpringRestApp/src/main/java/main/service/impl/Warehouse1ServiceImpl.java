package main.service.impl;

import main.entity.Good;
import main.entity.Warehouse1;
import main.repository.Warehouse1Repository;
import main.service.Warehouse1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Warehouse1ServiceImpl implements Warehouse1Service {

    @Autowired
    private Warehouse1Repository warehouse1Repository;

    @Override
    public List<Warehouse1> listGoods() {
        return (List<Warehouse1>) warehouse1Repository.findAll();
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
