package com.example.shelter.controllers;

import com.example.shelter.dto.DonationDto;
import com.example.shelter.entities.User;
import com.example.shelter.repositories.UserRepository;
import com.example.shelter.services.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class DonationRestController {

    private final DonationService donationService;
    private final UserRepository userRepository;

    public DonationRestController(DonationService donationService, UserRepository userRepository) {
        this.donationService = donationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/help/donate")
    public ResponseEntity<Void> processDonationJson(@RequestBody DonationDto donationDto, Principal principal) {
        User user = null;
        if (principal != null) {
            user = userRepository.findByEmail(principal.getName()).orElse(null);
        }

        donationService.saveDonation(donationDto.getAmount(), donationDto.getComment(), user);

        return ResponseEntity.ok().build();
    }
}