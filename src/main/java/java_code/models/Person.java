package java_code.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name cant be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",message="Your name should have this format: Name Second Name Surname")
    private String name;

    @Min(value=1900, message="Birthdate should be in range from 1900-2023")
    @Max(value=2023, message="Birthdate should be in range from 1900-2023")
    private int birthdate;

    public Person() {
    }

    public Person(int id, String name, int birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }
}
