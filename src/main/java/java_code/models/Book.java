package java_code.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    private Integer user_id;

    @NotEmpty(message = "Name cant be empty")
    private String name;

    @NotEmpty(message = "Author cant be empty")
    private String author;

    @Max(value=2023, message="Year shouldnt be greater then 2023")
    private int year;

    public Book() {
    }

    public Book(int id, Integer user_id, String name, String author,int year) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.author = author;
        this.year=year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
