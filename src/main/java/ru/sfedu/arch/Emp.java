package ru.sfedu.arch;

import com.opencsv.bean.CsvBindByName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Emp {
    @CsvBindByName(column = "ID")
    private long id;
    @CsvBindByName(column = "NAME")
    private String name;
    @CsvBindByName(column = "AGE")
    private int age;

    public Emp(){
        super();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    public void setAge(int age) {
        this.age = age;
    }

    public void setId() {
        this.id = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emp emp = (Emp) o;
        return Objects.equals(id, emp.id) && Objects.equals(name, emp.name) && Objects.equals(age, emp.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
