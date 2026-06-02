package com.example.shelter.services;

import com.example.shelter.entities.Supply;
import com.example.shelter.repositories.SupplyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplyService {
    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public List<Supply> getAllSupplies() { return supplyRepository.findAll(); }
    public void saveSupply(Supply supply) { supplyRepository.save(supply); }
    public void deleteSupply(Long id) { supplyRepository.deleteById(id); }
    public void updateSupply(Long id, Double amount) {
        supplyRepository.findById(id).ifPresent(s -> {
            s.setCurrentAmount(amount);
            supplyRepository.save(s);
        });
    }
}