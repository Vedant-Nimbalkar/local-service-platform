package com.localservice.model;

import jakarta.persistence.*;

@Entity
public class ServicePartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String serviceType;
    private int experience;
    private String password;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getServiceType() { return serviceType; }

    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public int getExperience() { return experience; }

    public void setExperience(int experience) { this.experience = experience; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}