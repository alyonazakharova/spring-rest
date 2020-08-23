package main.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_id", nullable = false)
    private Good good;

    @Column(name = "good_count")
    private Integer goodCount;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Sale() {
    }

    public Sale(Good good, Integer goodCount, LocalDateTime createDate) {
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

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", good=" + good +
                ", goodCount=" + goodCount +
                ", createDate=" + createDate +
                '}';
    }
}
