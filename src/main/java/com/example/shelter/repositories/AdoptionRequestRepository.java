package com.example.shelter.repositories;

import com.example.shelter.entities.AdoptionRequest;
import com.example.shelter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    List<AdoptionRequest> findByUser(User user); // Посмотреть свои заявки
}
