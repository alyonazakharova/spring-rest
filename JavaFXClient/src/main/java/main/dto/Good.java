package main.dto;

public class Good {

    private Integer id;
    private String name;
    private Double priority;

    public Good() {}

    public Good(Integer id, String name, Double priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public Good(String name, Double priority) {
        this.name = name;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }
}
