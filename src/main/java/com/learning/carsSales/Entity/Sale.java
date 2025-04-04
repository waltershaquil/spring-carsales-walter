package com.learning.carsSales.Entity;

import jakarta.persistence.*;

@Entity
@Table
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @Column
    private int price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @OneToOne
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

    public Sale() {
    }

    public Sale(Long saleId1, int price1, Buyer buyer, Car car) {
        saleId = saleId1;
        price = price1;
        this.buyer = buyer;
        this.car = car;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        saleId = saleId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int carprice) {
        price = carprice;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
