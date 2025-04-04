package com.learning.carsSales.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Buyer")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyerId;
    @Column
    private String Name;
    @Column
    private String Address;
    @Column
    private String Phone;
    @Column
    private String Email;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

    public Buyer() {
    }

    public Buyer(int buyerId, String name, String address, String phone, String email) {
        this.buyerId = buyerId;
        Name = name;
        Address = address;
        Phone = phone;
        Email = email;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
