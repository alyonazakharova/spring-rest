package main.dto;

import java.time.LocalDateTime;

public class Sale {

    private Integer id;
    private Good good;
    private Integer goodCount;
    private LocalDateTime createDate;

    public Sale() {}

    public Sale(Good good, Integer goodCount, LocalDateTime createDate) {
        this.good = good;
        this.goodCount = goodCount;
        this.createDate = createDate;
    }

    public Sale(Integer id, Good good, Integer goodCount, LocalDateTime createDate) {
        this.id = id;
        this.good = good;
        this.goodCount = goodCount;
        this.createDate = createDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
