package com.example.shelter.services;

import com.example.shelter.entities.Donation;
import com.example.shelter.entities.User;
import com.example.shelter.repositories.DonationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public void saveDonation(Double amount, String comment, User user) {
        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setComment(comment);
        donation.setUser(user);
        donation.setDonatedAt(LocalDateTime.now());
        donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() { return donationRepository.findAll(); }

    public double getTotalDonationsSum() {
        return donationRepository.findAll().stream()
                .mapToDouble(Donation::getAmount).sum();
    }

    public List<Donation> findByUser(User user) {
        return donationRepository.findByUser(user);
    }
}