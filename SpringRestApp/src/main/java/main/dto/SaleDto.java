package main.dto;

import main.entity.Sale;

import java.time.LocalDateTime;

public class SaleDto {

    private Integer id;
    private Integer goodId;
    private Integer goodCount;
    private LocalDateTime createDate;

    public SaleDto() {}

    public SaleDto(Integer id, Integer goodId, Integer goodCount, LocalDateTime createDate) {
        this.id = id;
        this.goodId = goodId;
        this.goodCount = goodCount;
        this.createDate = createDate;
    }

    public SaleDto(Sale sale) {
        this.id = sale.getId();
        this.goodId = sale.getGood().getId();
        this.goodCount = sale.getGoodCount();
        this.createDate = sale.getCreateDate();
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
