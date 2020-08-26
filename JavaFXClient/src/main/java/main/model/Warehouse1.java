package main.model;

public class Warehouse1 {
    private Integer id;
    private Goods goodId;
    private Integer goodCount;

    public Warehouse1() {}

    public Warehouse1(Goods goodId, Integer goodCount) {
        this.goodId = goodId;
        this.goodCount = goodCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoodId() {
        return goodId;
    }

    public void setGoodId(Goods goodId) {
        this.goodId = goodId;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }
}
