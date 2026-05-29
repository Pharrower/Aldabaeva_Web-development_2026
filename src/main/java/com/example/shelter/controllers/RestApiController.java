package com.example.shelter.controllers;

import com.example.shelter.entities.*;
import com.example.shelter.services.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "REST API", description = "Эндпоинты для мобильного приложения или фронтенда")
public class RestApiController {

    @Autowired
    private AnimalService animalService;

    @Operation(summary = "Все животные")
    @GetMapping("/animals")
    public List<Animal> getAll() { return animalService.getAllAvailableAnimals(); }

    @Operation(summary = "Статистика приюта")
    @GetMapping("/stats/adopted")
    public long getStats() { return animalService.getAdoptedCount(); }

    @Operation(summary = "Список волонтеров")
    @GetMapping("/volunteers")
    public List<VolunteerRequest> getVolunteers() { return animalService.getAllVolunteerRequests(); }

    @Operation(summary = "Список донатов")
    @GetMapping("/donations")
    public List<Donation> getDonations() { return animalService.getAllDonations(); }

    @Operation(summary = "Склад")
    @GetMapping("/supplies")
    public List<Supply> getSupplies() { return animalService.getAllSupplies(); }

    @Operation(summary = "Заявки на пристройство")
    @GetMapping("/requests")
    public List<AdoptionRequest> getRequests() { return animalService.getAllRequests(); }
}