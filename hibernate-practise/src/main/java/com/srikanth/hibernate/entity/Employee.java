package com.srikanth.hibernate.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @Column(name = "MID", unique = true, nullable = false, length = 100)
    private String employeeId;

    @Column(name = "NAME", unique = true, nullable = false, length = 200)
    private String employeeName;

    // Using String to avoid extra validations. Confining to Hibernate ORM usage practise
    @Column(name = "JOIN_DATE", unique = true, nullable = false, length = 15)
    private String joiningDate;

    @Column(name = "EMAIL_ID", unique = true, nullable = false, length = 200)
    private String emailId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "EMPLOYEES_EVENTS",
            joinColumns = {@JoinColumn(name = "MID")},
            inverseJoinColumns = {@JoinColumn(name = "EVENT_ID")}
    )
    Set<Event> events = new HashSet<>();

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(joiningDate, employee.joiningDate) &&
                Objects.equals(emailId, employee.emailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, joiningDate, emailId);
    }
}
