package main.dto;

import main.entity.Warehouse2;

public class Warehouse2Dto {

    private Integer id;
    private Integer goodId;
    private Integer goodCount;

    public Warehouse2Dto() {}

    public Warehouse2Dto(Warehouse2 warehouse2) {
        this.id = warehouse2.getId();
        this.goodId = warehouse2.getGood().getId();
        this.goodCount = warehouse2.getGoodCount();
    }

    public Warehouse2Dto(Integer id, Integer goodId, Integer goodCount) {
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
