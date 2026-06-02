package com.example.shelter.controllers;

import com.example.shelter.entities.Supply;
import com.example.shelter.entities.VolunteerRequest;
import com.example.shelter.services.DonationService;
import com.example.shelter.services.SupplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShelterAdminController {

    private final SupplyService supplyService;
    private final DonationService donationService;

    // Используем конструктор для внедрения сервисов
    public ShelterAdminController(SupplyService supplyService, DonationService donationService) {
        this.supplyService = supplyService;
        this.donationService = donationService;
    }

    @GetMapping("/help")
    public String showHelpPage(Model model) {
        model.addAttribute("supplies", supplyService.getAllSupplies());
        model.addAttribute("volunteerRequest", new VolunteerRequest());
        return "help";
    }

    @GetMapping("/admin/donations")
    public String showDonations(Model model) {
        model.addAttribute("donations", donationService.getAllDonations());
        // Всю логику подсчета суммы перенесли в DonationService,
        // поэтому тут просто берем готовое значение
        model.addAttribute("totalAmount", donationService.getTotalDonationsSum());
        return "admin-donations";
    }

    @GetMapping("/admin/supplies")
    public String manageSupplies(Model model) {
        model.addAttribute("supplies", supplyService.getAllSupplies());
        model.addAttribute("newSupply", new Supply());
        return "admin-supplies";
    }

    @PostMapping("/admin/supplies/save")
    public String saveSupply(@ModelAttribute("newSupply") Supply supply) {
        supplyService.saveSupply(supply);
        return "redirect:/admin/supplies";
    }

    @GetMapping("/admin/supplies/delete/{id}")
    public String deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return "redirect:/admin/supplies";
    }
}