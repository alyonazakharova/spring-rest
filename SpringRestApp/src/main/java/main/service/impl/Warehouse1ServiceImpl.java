package main.service.impl;

import main.entity.Warehouse1;
import main.exception.GoodNotFoundException;
import main.repository.Warehouse1Repository;
import main.service.Warehouse1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Warehouse1ServiceImpl implements Warehouse1Service {

    @Autowired
    private Warehouse1Repository warehouse1Repository;

    @Override
    public List<Warehouse1> listGoods() {
        return (List<Warehouse1>) warehouse1Repository.findAll();
    }

    @Override
    public Warehouse1 find(Integer id) {
        Optional<Warehouse1> optionalW1 = warehouse1Repository.findById(id);
        if (optionalW1.isPresent()) {
            return optionalW1.get();
        } else {
            throw new GoodNotFoundException("Good not found in warehouse 1");
        }
    }

    @Override
    public Warehouse1 addGood(Warehouse1 w1Good) {
        return warehouse1Repository.save(w1Good);
    }

    @Override
    public Warehouse1 updateGood(Integer id, Warehouse1 w1Good) {
        Optional<Warehouse1> optionalW1Good = warehouse1Repository.findById(id);
        if (optionalW1Good.isPresent()) {
            Warehouse1 w1 = optionalW1Good.get();
            w1.setGood(w1Good.getGood());
            w1.setGoodCount(w1Good.getGoodCount());
            return warehouse1Repository.saveAndFlush(w1);
        } else {
            throw new GoodNotFoundException("Good not found in warehouse 1");
        }
    }

    @Override
    public void deleteGood(Integer id) {
        try {
            warehouse1Repository.deleteById(id);
        } catch (Exception e) {
            throw new GoodNotFoundException("Good not found in warehouse 1");
        }
    }
}
