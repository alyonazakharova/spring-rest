package main.entity;

import javax.persistence.*;

@Entity
@Table(name = "warehouse2")
public class Warehouse2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_id", unique = true)
    private Good good;

    @Column(name = "good_count")
    private Integer goodCount;

    public Warehouse2() {
    }

    public Warehouse2(Good good, Integer goodCount) {
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
