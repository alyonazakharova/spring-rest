package main.dto;

public class Warehouse1Dto {

    private Integer id;
    private Integer goodId;
    private Integer goodCount;

    public Warehouse1Dto() {}

    public Warehouse1Dto(Integer id, Integer goodId, Integer goodCount) {
        this.id = id;
        this.goodId = goodId;
        this.goodCount = goodCount;
    }

    public Warehouse1Dto(Good good, Integer goodCount) {
        this.goodId = good.getId();
        this.goodCount = goodCount;
    }

    public Warehouse1Dto(Integer goodId, Integer goodCount) {
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
