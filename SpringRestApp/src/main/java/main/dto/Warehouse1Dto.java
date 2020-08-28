package main.dto;

import main.entity.Warehouse1;

public class Warehouse1Dto {

    private Integer id;
    private Integer goodId;
    private Integer goodCount;

    public Warehouse1Dto() {}

    public Warehouse1Dto(Warehouse1 warehouse1) {
        this.id = warehouse1.getId();
        this.goodId = warehouse1.getGood().getId();
        this.goodCount = warehouse1.getGoodCount();
    }

    public Warehouse1Dto(Integer id, Integer goodId, Integer goodCount) {
        this.id = id;
        this.goodId = goodId;
        this.goodCount = goodCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }
}
