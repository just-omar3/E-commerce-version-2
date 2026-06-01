package org.example.Ecommerce2.Employee;

import jakarta.persistence.*;
import org.example.Ecommerce2.User.UserModels.Profile;
import org.example.Ecommerce2.User.UserModels.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class EmployeeProfile implements Profile {


    @Id
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String employeeCode;
    private String department;
    private LocalDate hireDate;
    private BigDecimal salary;

    public EmployeeProfile() {}

    public EmployeeProfile(User user) {
        this.user = user;
        this.id = user.getUserId();
    }


    @Override
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; this.id = user.getUserId(); }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
}