package com.example.demo.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.model.customer.Customer;
import com.example.demo.model.department.Department;
import com.example.demo.model.infologin.InfoLogin;
import com.example.demo.model.project.Project;
import com.example.demo.model.role.Role;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String userIdHash;


    @NotBlank(message = "FirstName is mandatory")
    @Column(name = "first_name")
    @Size(max = 40)
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    @Column(name = "last_name")
    @Size(max = 40)
    private String lastName;

    @NotBlank(message = "Userame is mandatory")
    @Column(name = "username")
    @Size(max = 15)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be 8 or more characters in length")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @NaturalId
    @Size(max = 40)
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @Column(name = "salary_num")
    private Long salaryNum;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_department", //Tạo ra một join Table tên là "address_person"
            joinColumns = @JoinColumn(name = "user_id"),  // TRong đó, khóa ngoại chính là address_id trỏ tới class hiện tại (Address)
            inverseJoinColumns = @JoinColumn(name = "department_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Person)
    )
    private List<Department> departments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InfoLogin> infoLogin;

    public User() {

    }

    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getSalaryNum() {
        return salaryNum;
    }

    public void setSalaryNum(Long salaryNum) {
        this.salaryNum = salaryNum;
    }

    public List<Customer> getCustomers() {
        return customers == null ? null : new ArrayList<>(customers);
    }

    public void setCustomers(List<Customer> customers) {
        if (customers == null) {
            this.customers = null;
        } else {
            this.customers = Collections.unmodifiableList(customers);
        }
    }

    public List<Project> getProjects() {
        return projects == null ? null : new ArrayList<>(projects);
    }

    public void setProjects(List<Project> projects) {
        if (projects == null) {
            this.projects = null;
        } else {
            this.projects = Collections.unmodifiableList(projects);
        }
    }

    public List<Department> getDepartments() {
        return departments == null ? null : new ArrayList<>(departments);
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<InfoLogin> getInfoLogin() {
        return infoLogin;
    }

    public void setInfoLogin(List<InfoLogin> infoLogin) {
        this.infoLogin = infoLogin;
    }

    public String getUserIdHash() {
        return userIdHash;
    }

    public void setUserIdHash(String userId) {
        this.userIdHash = userId;
    }
}
