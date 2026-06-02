package com.example.shelter.controllers;

import com.example.shelter.entities.*;
import com.example.shelter.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final AnimalService animalService;
    private final DonationService donationService;
    private final UserService userService;
    private final VolunteerService volunteerService;

    public MainController(AnimalService animalService,
                          DonationService donationService,
                          UserService userService,
                          VolunteerService volunteerService) {
        this.animalService = animalService;
        this.donationService = donationService;
        this.userService = userService;
        this.volunteerService = volunteerService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Animal> allAnimals = animalService.getAllAvailableAnimals();

        model.addAttribute("listAnimals", allAnimals.stream().limit(6).collect(Collectors.toList()));

        if (!allAnimals.isEmpty()) {
            List<Animal> shuffled = new java.util.ArrayList<>(allAnimals);
            Collections.shuffle(shuffled);
            model.addAttribute("heroAnimal", shuffled.get(0));
        }

        return "index";
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userService.findByEmail(principal.getName());

        model.addAttribute("myRequests", animalService.getUserRequests(user));
        model.addAttribute("myDonations", donationService.findByUser(user));

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@RequestParam("fullName") String newName,
                                    @RequestParam(value = "newPassword", required = false) String newPassword,
                                    Principal principal) {
        if (principal == null) return "redirect:/login";

        userService.updateProfile(principal.getName(), newName, newPassword);

        return "redirect:/profile?success";
    }

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        model.addAttribute("adoptedCount", animalService.getAdoptedCount());
        model.addAttribute("inShelterCount", animalService.getInShelterCount());
        model.addAttribute("volunteersCount", volunteerService.getVolunteersCount());
        model.addAttribute("totalDonations", donationService.getTotalDonationsSum());
        return "about";
    }

    @ModelAttribute("userName")
    public String getUserName(Principal principal) {
        if (principal == null) return "Гость";
        return userService.findByEmail(principal.getName()).getFirstName();
    }
}