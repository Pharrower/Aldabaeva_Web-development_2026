package com.example.shelter.controllers;

import com.example.shelter.entities.*;
import com.example.shelter.repositories.UserRepository;
import com.example.shelter.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;

@Controller
public class AnimalAdminController {

    private final AnimalService animalService;
    private final VolunteerService volunteerService;
    private final UserRepository userRepository;

    // Конструкторная инъекция - это стандарт современной разработки
    public AnimalAdminController(AnimalService animalService,
                                 VolunteerService volunteerService,
                                 UserRepository userRepository) {
        this.animalService = animalService;
        this.volunteerService = volunteerService;
        this.userRepository = userRepository;
    }

    @GetMapping("/catalog")
    public String showCatalog(@RequestParam(required = false) String species,
                              @RequestParam(required = false) String gender,
                              Model model) {
        model.addAttribute("listAnimals", animalService.getFilteredAnimals(species, gender));
        return "catalog";
    }

    @GetMapping("/admin/add-animal")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "add-animal";
    }

    @PostMapping("/admin/add-animal")
    public String saveAnimal(Animal animal, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String imagePath = animalService.saveImage(imageFile);
        if (imagePath != null) {
            animal.setImageUrl(imagePath);
        }
        animalService.saveAnimal(animal);
        return "redirect:/catalog";
    }

    @GetMapping("/admin/edit-animal/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("animal", animalService.getAnimalById(id));
        return "edit-animal";
    }

    @PostMapping("/admin/edit-animal/{id}")
    public String updateAnimal(@PathVariable Long id,
                               @ModelAttribute("animal") Animal animal,
                               @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Animal existingAnimal = animalService.getAnimalById(id);

        existingAnimal.setName(animal.getName());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setGender(animal.getGender());
        existingAnimal.setAge(animal.getAge());
        existingAnimal.setWeight(animal.getWeight());
        existingAnimal.setDescription(animal.getDescription());

        if (!imageFile.isEmpty()) {
            String imagePath = animalService.saveImage(imageFile);
            existingAnimal.setImageUrl(imagePath);
        }

        animalService.saveAnimal(existingAnimal);
        return "redirect:/catalog";
    }

    @PostMapping("/adopt/{id}")
    public String submitRequest(@PathVariable Long id, @RequestParam String message, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Animal animal = animalService.getAnimalById(id);

        animalService.createRequest(user, animal, message);
        return "redirect:/catalog?success_request";
    }

    @GetMapping("/admin/requests")
    public String showAdminRequests(Model model) {
        model.addAttribute("allRequests", animalService.getAllRequests());
        return "admin-requests";
    }

    @PostMapping("/admin/requests/approve/{id}")
    public String approveRequest(@PathVariable Long id) {
        animalService.approveRequest(id);
        return "redirect:/admin/requests";
    }

    @PostMapping("/admin/requests/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        animalService.rejectRequest(id);
        return "redirect:/admin/requests";
    }

    @PostMapping("/help/volunteer")
    public String submitVolunteer(VolunteerRequest request, Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        volunteerService.saveVolunteerRequest(request, user);
        return "redirect:/help?success";
    }

    @GetMapping("/admin/volunteers")
    public String showVolunteerRequests(Model model) {
        model.addAttribute("volunteers", volunteerService.getAllVolunteerRequests());
        return "admin-volunteers";
    }
}