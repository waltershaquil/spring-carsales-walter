package com.learning.carsSales.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private int year;

    @Column
    private int mileage;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Sale sale;

    public Car() {
    }
    public Car(Long carId, String make, String model, int year, int mileage) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public Car(String make, String model, int year, int mileage) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
