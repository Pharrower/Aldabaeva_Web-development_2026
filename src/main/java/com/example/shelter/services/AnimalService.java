package com.example.shelter.services;

import com.example.shelter.dto.AdoptionRequestDto;
import com.example.shelter.entities.*;
import com.example.shelter.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AdoptionRequestRepository requestRepository;

    public AnimalService(AnimalRepository animalRepository, AdoptionRequestRepository requestRepository) {
        this.animalRepository = animalRepository;
        this.requestRepository = requestRepository;
    }

    public List<Animal> getAllAvailableAnimals() { return animalRepository.findAllByStatus("AVAILABLE"); }

    public void saveAnimal(Animal animal) { animalRepository.save(animal); }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Животное не найдено"));
    }

    public List<Animal> getFilteredAnimals(String species, String gender) {
        String status = "AVAILABLE";
        if ((species == null || species.isEmpty()) && (gender == null || gender.isEmpty())) return animalRepository.findAllByStatus(status);
        if (species != null && !species.isEmpty() && (gender == null || gender.isEmpty())) return animalRepository.findBySpeciesAndStatus(species, status);
        if (gender != null && !gender.isEmpty() && (species == null || species.isEmpty())) return animalRepository.findByGenderAndStatus(gender, status);
        return animalRepository.findBySpeciesAndGenderAndStatus(species, gender, status);
    }

    public void createRequest(User user, Animal animal, String message) {
        AdoptionRequest request = new AdoptionRequest();
        request.setUser(user);
        request.setAnimal(animal);
        request.setMessage(message);
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());
        requestRepository.save(request);
    }

    public List<AdoptionRequest> getUserRequests(User user) { return requestRepository.findByUser(user); }

    public List<AdoptionRequest> getAllRequests() { return requestRepository.findAll(); }

    @Transactional
    public void approveRequest(Long requestId) {
        AdoptionRequest request = requestRepository.findById(requestId).orElseThrow();
        request.setStatus("APPROVED");
        Animal animal = request.getAnimal();
        animal.setStatus("ADOPTED");
        animalRepository.save(animal);
        requestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        requestRepository.findById(requestId).ifPresent(r -> {
            r.setStatus("REJECTED");
            requestRepository.save(r);
        });
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get("uploads/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        return "/" + fileName;
    }

    public long getAdoptedCount() { return animalRepository.countByStatus("ADOPTED"); }
    public long getInShelterCount() { return animalRepository.countByStatus("AVAILABLE"); }

    public void createRequestFromDto(AdoptionRequestDto dto) {
        // 1. Находим животное по ID
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Животное не найдено"));

        // 2. Создаем заявку
        AdoptionRequest request = new AdoptionRequest();
        request.setAnimal(animal);
        request.setMessage(dto.getMessage());
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());

        requestRepository.save(request);
    }
}