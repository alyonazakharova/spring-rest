package main.model;

import java.time.LocalDateTime;

public class Sales {
    private Integer id;
    private Goods goodId;
    private Integer goodCount;
    private LocalDateTime createDate;

    public Sales() {}

    public Sales(Goods goodId, Integer goodCount, LocalDateTime createDate) {
        this.goodId = goodId;
        this.goodCount = goodCount;
        this.createDate = createDate;
    }

    public Sales(Integer id, Goods goodId, Integer goodCount, LocalDateTime createDate) {
        this.id = id;
        this.goodId = goodId;
        this.goodCount = goodCount;
        this.createDate = createDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
