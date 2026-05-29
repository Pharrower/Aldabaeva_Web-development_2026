package com.example.shelter.repositories;

import com.example.shelter.entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
