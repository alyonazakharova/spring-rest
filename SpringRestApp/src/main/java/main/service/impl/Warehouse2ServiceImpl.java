package main.service.impl;

import main.entity.Warehouse2;
import main.exception.GoodNotFoundException;
import main.repository.Warehouse2Repository;
import main.service.Warehouse2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Warehouse2ServiceImpl implements Warehouse2Service {

    @Autowired
    private Warehouse2Repository warehouse2Repository;

    @Override
    public List<Warehouse2> listGoods() {
        return (List<Warehouse2>) warehouse2Repository.findAll();
    }

    @Override
    public Warehouse2 addGood(Warehouse2 w2Good) {
        return warehouse2Repository.save(w2Good);
    }

    @Override
    public Warehouse2 updateGood(Integer id, Warehouse2 w2Good) {
        Optional<Warehouse2> optionalW2Good = warehouse2Repository.findById(id);
        if (optionalW2Good.isPresent()) {
            Warehouse2 w2 = optionalW2Good.get();
            w2.setGood(w2Good.getGood());
            w2.setGoodCount(w2Good.getGoodCount());
            return warehouse2Repository.saveAndFlush(w2);
        } else {
            throw new GoodNotFoundException("Good not found in warehouse 2");
        }
    }

    @Override
    public void deleteFood(Integer id) {
        //как должно все удаляться???
    }
}
