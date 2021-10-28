package ru.sfedu.arch;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Product {
    @CsvBindByName(column = "ID")
    private long id;
    @CsvBindByName(column = "NAME")
    private String name;
    @CsvBindByName(column = "PRICE")
    private int price;

    public Product(){
        super();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setPrice(int price) {
        this.price = price;
    }

    public void setId() {
        this.id = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + price + '\'' +
                '}';
    }
}
