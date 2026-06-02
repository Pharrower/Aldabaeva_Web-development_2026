package com.example.shelter.controllers;

import com.example.shelter.dto.AdoptionRequestDto;
import com.example.shelter.entities.*;
import com.example.shelter.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "REST API", description = "Эндпоинты для мобильного приложения или фронтенда")
public class RestApiController {

    private final AnimalService animalService;
    private final VolunteerService volunteerService;
    private final DonationService donationService;
    private final SupplyService supplyService;

    public RestApiController(AnimalService animalService,
                             VolunteerService volunteerService,
                             DonationService donationService,
                             SupplyService supplyService) {
        this.animalService = animalService;
        this.volunteerService = volunteerService;
        this.donationService = donationService;
        this.supplyService = supplyService;
    }

    @Operation(summary = "Получить список доступных животных")
    @GetMapping("/animals")
    public List<Animal> getAll() { return animalService.getAllAvailableAnimals(); }

    @Operation(summary = "Получить статистику адопции")
    @GetMapping("/stats/adopted")
    public long getStats() { return animalService.getAdoptedCount(); }

    @Operation(summary = "Получить список волонтеров")
    @GetMapping("/volunteers")
    public List<VolunteerRequest> getVolunteers() { return volunteerService.getAllVolunteerRequests(); }

    @Operation(summary = "Получить список пожертвований")
    @GetMapping("/donations")
    public List<Donation> getDonations() { return donationService.getAllDonations(); }

    @Operation(summary = "Получить состояние склада")
    @GetMapping("/supplies")
    public List<Supply> getSupplies() { return supplyService.getAllSupplies(); }

    @Operation(summary = "Получить все заявки на пристройство")
    @GetMapping("/requests")
    public List<AdoptionRequest> getRequests() { return animalService.getAllRequests(); }


    @Operation(summary = "Подать заявку на адопцию", description = "Создает новую заявку от имени пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно принята")
    })
    @PostMapping("/requests/apply")
    public ResponseEntity<String> applyForAdoption(@RequestBody AdoptionRequestDto requestDto) {
        animalService.createRequestFromDto(requestDto);
        return ResponseEntity.ok("Заявка успешно создана!");
    }
}