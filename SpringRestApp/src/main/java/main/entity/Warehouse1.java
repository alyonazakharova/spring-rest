package main.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "warehouse1")
public class Warehouse1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_id", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Good good;

    @Column(name = "good_count")
    private Integer goodCount;

    public Warehouse1() {
    }

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
