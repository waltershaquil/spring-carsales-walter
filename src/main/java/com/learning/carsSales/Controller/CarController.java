package com.learning.carsSales.Controller;

import com.learning.carsSales.Entity.Car;
import com.learning.carsSales.Entity.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173", "https://car-sales-react-fawn.vercel.app" })
@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping
    public Car CreateCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        return  carRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setMake(carDetails.getMake());
            car.setModel(carDetails.getModel());
            car.setYear(carDetails.getYear());
            car.setMileage(carDetails.getMileage());

            Car updatedCar = carRepository.save(car);

            return ResponseEntity.ok(updatedCar);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}