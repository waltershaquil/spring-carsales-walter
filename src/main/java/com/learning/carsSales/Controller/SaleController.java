package com.learning.carsSales.Controller;

import com.learning.carsSales.Entity.Buyer;
import com.learning.carsSales.Entity.BuyerRepository;
import com.learning.carsSales.Entity.Car;
import com.learning.carsSales.Entity.CarRepository;
import com.learning.carsSales.Entity.Sale;
import com.learning.carsSales.Entity.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173" })
@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        // Handle Buyer
        if (sale.getBuyer() != null && sale.getBuyer().getBuyerId() != 0) { // Use getId() instead of getBuyerId()
            // Fetch the existing Buyer from the database
            Buyer buyer = buyerRepository.findById(sale.getBuyer().getBuyerId()) // Use getId()
                    .orElseThrow(() -> new IllegalArgumentException("Buyer not found with ID: " + sale.getBuyer().getBuyerId()));
            sale.setBuyer(buyer); // Set the fetched Buyer to the Sale
        } else {
            throw new IllegalArgumentException("Buyer ID is required and must be valid");
        }

        // Handle Car
        if (sale.getCar() != null && sale.getCar().getCarId() != 0) { // Use getId() instead of getCarId()
            // Fetch the existing Car from the database
            Car car = carRepository.findById(Math.toIntExact(sale.getCar().getCarId())) // Use getId()
                    .orElseThrow(() -> new IllegalArgumentException("Car not found with ID: " + sale.getCar().getCarId()));
            sale.setCar(car); // Set the fetched Car to the Sale
        } else {
            throw new IllegalArgumentException("Car ID is required and must be valid");
        }

        // Save the Sale
        return saleRepository.save(sale);
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable int id) {
        return saleRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable int id, @RequestBody Sale saleDetails) {
        return saleRepository.findById(Long.valueOf(id)).map(sale -> {
            // Handle Buyer
            if (saleDetails.getBuyer() != null) {
                Buyer buyer = saleDetails.getBuyer();
                if (buyer.getBuyerId() != 0) {
                    // Fetch the existing Buyer from the database
                    Buyer existingBuyer = buyerRepository.findById(buyer.getBuyerId())
                            .orElseThrow(() -> new IllegalArgumentException("Buyer not found with ID: " + buyer.getBuyerId()));
                    sale.setBuyer(existingBuyer);
                } else {
                    // Save the Buyer first if it's a new entity (Transient)
                    Buyer savedBuyer = buyerRepository.save(buyer);
                    sale.setBuyer(savedBuyer);
                }
            }

            // Handle Car
            if (saleDetails.getCar() != null) {
                Car car = saleDetails.getCar();
                if (car.getCarId() != null && car.getCarId() != 0) {
                    // Fetch the existing Car from the database
                    Car existingCar = carRepository.findById(Math.toIntExact(car.getCarId()))
                            .orElseThrow(() -> new IllegalArgumentException("Car not found with ID: " + car.getCarId()));
                    sale.setCar(existingCar);
                } else {
                    // Save the Car first if it's a new entity (Transient)
                    Car savedCar = carRepository.save(car);
                    sale.setCar(savedCar);
                }
            }

            // Update other fields
            sale.setPrice(saleDetails.getPrice());

            Sale updatedSale = saleRepository.save(sale);
            return ResponseEntity.ok(updatedSale);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleById(@PathVariable int id) {
        if (saleRepository.existsById(Long.valueOf(id))) {
            saleRepository.deleteById(Long.valueOf(id));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}