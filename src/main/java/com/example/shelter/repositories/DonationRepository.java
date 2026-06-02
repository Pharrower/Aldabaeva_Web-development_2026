package com.example.shelter.repositories;

import com.example.shelter.entities.Donation;
import com.example.shelter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUser(User user);
}