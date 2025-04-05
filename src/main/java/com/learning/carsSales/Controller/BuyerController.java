package com.learning.carsSales.Controller;

import com.learning.carsSales.Entity.Buyer;
import com.learning.carsSales.Entity.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:5173", "https://car-sales-react-fawn.vercel.app" })
@RestController
@RequestMapping("/api/buyers")
public class BuyerController {
    @Autowired
    private BuyerRepository buyerRepository;

    @PostMapping
    public Buyer CreateBuyer(@RequestBody Buyer buyer){
        return buyerRepository.save(buyer);
    }

    @GetMapping
    public List<Buyer> getAllBuyers(){
        return buyerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Buyer getBuyerById(@PathVariable int id){
        return buyerRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buyer> UpdateBuyer(@PathVariable Integer id, @RequestBody Buyer buyerDetails){
        return buyerRepository.findById(id).map(buyer-> {

            buyer.setName(buyerDetails.getName());
            buyer.setAddress(buyerDetails.getAddress());
            buyer.setPhone(buyerDetails.getPhone());
            buyer.setEmail(buyerDetails.getEmail());

            Buyer updatedBuyer = buyerRepository.save(buyer);

            return ResponseEntity.ok(updatedBuyer);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable int id){
        if(buyerRepository.existsById(id)){
            buyerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}
