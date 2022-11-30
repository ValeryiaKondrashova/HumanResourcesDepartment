package com.hrd.HumanResourcesDepartment.models;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName, lastName, patronymic;
    private int experience;
    private String startWork, telephone, email;
    private int timeWork;

    private String position;


    public Employee() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getStartWork() {
        return startWork;
    }

    public void setStartWork(String startWork) {
        this.startWork = startWork;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(int timeWork) {
        this.timeWork = timeWork;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee(String firstName, String lastName, String patronymic, int experience, String startWork, String telephone, String email, int timeWork, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.experience = experience;
        this.startWork = startWork;
        this.telephone = telephone;
        this.email = email;
        this.timeWork = timeWork;
        this.position = position;
    }

    public Employee(Long id, String firstName, String lastName, String patronymic, int experience, String startWork, String telephone, String email, int timeWork, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.experience = experience;
        this.startWork = startWork;
        this.telephone = telephone;
        this.email = email;
        this.timeWork = timeWork;
        this.position = position;
    }
}
