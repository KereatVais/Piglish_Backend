package com.example.AuthMicroservice.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;

@Entity(name = "Person")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

//    @Min(value = 1900, message = "Год рождения должен быть больше 1900")
//    @Column(name = "year_of_birth")
//    private int yearOfBirth;

    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Email
    @Column(name = "email")
    private String email;

    @Size(min = 2, message = "Название страны должно содержать больше 2-х символов")
    @Column(name = "country")
    private String country;

    @Column(name="role")
    private String role;

    public User() {
    }

    public User(String username, String password, Date dateOfBirth, String email, String country) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public int getYearOfBirth() {
//        return yearOfBirth;
//    }
//
//    public void setYearOfBirth(int yearOfBirth) {
//        this.yearOfBirth = yearOfBirth;
//    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", yearOfBirth=" + yearOfBirth +
                '}';
    }
}
