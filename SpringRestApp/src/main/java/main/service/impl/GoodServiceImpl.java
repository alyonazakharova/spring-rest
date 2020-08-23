package main.service.impl;

import main.entity.Good;
import main.exception.GoodNotFoundException;
import main.repository.GoodRepository;
import main.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodRepository goodRepository;

    @Override
    public List<Good> list() {
        return (List<Good>) goodRepository.findAll();
    }

    @Override
    public Good find(Integer id) {
        Optional<Good> optionalGood = goodRepository.findById(id);
        if (optionalGood.isPresent()) {
            return optionalGood.get();
        } else {
            throw new GoodNotFoundException("Good not found");
        }
    }

    @Override
    public Good add(Good good) {
        return goodRepository.save(good);
    }

    @Override
    public void delete(Integer id) {
        try {
            goodRepository.deleteById(id);
        } catch (Exception exception)
        {
            throw new GoodNotFoundException("Good not found");
        }
    }

    @Override
    public Good update(Integer id, Good good) {
        Optional<Good> optionalGood = goodRepository.findById(id);
        if (optionalGood.isPresent()) {
            Good g = optionalGood.get();
            g.setName(good.getName());
            g.setPriority(good.getPriority());
            return goodRepository.saveAndFlush(g);
        } else {
            throw new GoodNotFoundException("Good not found");
        }
    }
}
