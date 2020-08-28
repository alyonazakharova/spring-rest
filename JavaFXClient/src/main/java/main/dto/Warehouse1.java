package main.dto;

public class Warehouse1 {

    private Integer id;
    private Good good;
    private Integer goodCount;

    public Warehouse1() {}

    public Warehouse1(Good good, Integer goodCount) {
        this.good = good;
        this.goodCount = goodCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }
}
