package main.service;

import main.entity.Good;

import java.util.List;

public interface GoodService {
    List<Good> list();
    Good find(Integer id);
    Good add(Good good);
    void delete(Integer id);
    Good update(Integer id, Good good);
}
