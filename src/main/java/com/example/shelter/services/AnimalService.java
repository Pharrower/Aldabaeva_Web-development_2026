package com.example.shelter.services;

import com.example.shelter.entities.*;
import com.example.shelter.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    // Метод для получения всех животных из базы
    public List<Animal> getAllAvailableAnimals() {
        return animalRepository.findAllByStatus("AVAILABLE");
    }

    // Метод для сохранения нового животного
    public void saveAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    public List<Animal> getFilteredAnimals(String species, String gender) {
        String status = "AVAILABLE"; // Фильтруем только тех, кто в приюте

        if ((species == null || species.isEmpty()) && (gender == null || gender.isEmpty())) {
            return animalRepository.findAllByStatus(status);
        } else if (species != null && !species.isEmpty() && (gender == null || gender.isEmpty())) {
            return animalRepository.findBySpeciesAndStatus(species, status);
        } else if (gender != null && !gender.isEmpty() && (species == null || species.isEmpty())) {
            return animalRepository.findByGenderAndStatus(gender, status);
        } else {
            return animalRepository.findBySpeciesAndGenderAndStatus(species, gender, status);
        }
    }

    // Заявки животных
    @Autowired
    private AdoptionRequestRepository requestRepository;

    public void createRequest(User user, Animal animal, String message) {
        AdoptionRequest request = new AdoptionRequest();
        request.setUser(user);
        request.setAnimal(animal);
        request.setMessage(message);
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());
        requestRepository.save(request);
    }

    // Для пользователя: только его заявки
    public List<AdoptionRequest> getUserRequests(User user) {
        return requestRepository.findByUser(user);
    }

    // Для админа: вообще все заявки из базы
    public List<AdoptionRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    public void approveRequest(Long requestId) {
        AdoptionRequest request = requestRepository.findById(requestId).get();
        request.setStatus("APPROVED");

        Animal animal = request.getAnimal();
        animal.setStatus("ADOPTED");

        animalRepository.save(animal);
        requestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        AdoptionRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        request.setStatus("REJECTED");
        requestRepository.save(request);
    }

    // Загрузка фото хвостиков
    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        // Создаем уникальное имя файла, чтобы картинки не перезаписывались
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);

        // Физически сохраняем файл на диск
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return "/" + fileName; // Возвращаем путь для базы данных
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Животное не найдено"));
    }


    @Autowired
    private VolunteerRepository volunteerRepository;

    public void saveVolunteerRequest(VolunteerRequest request, User user) {
        request.setUser(user);
        request.setSubmittedAt(LocalDateTime.now());
        request.setStatus("NEW");
        volunteerRepository.save(request);
    }

    public List<VolunteerRequest> getAllVolunteerRequests() {
        return volunteerRepository.findAll();
    }

    @Autowired
    private DonationRepository donationRepository;

    public void saveDonation(Double amount, String comment, User user) {
        Donation donation = new Donation();
        donation.setAmount(amount);
        donation.setComment(comment);
        donation.setUser(user);
        donation.setDonatedAt(LocalDateTime.now());
        donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public long getAdoptedCount() {
        return animalRepository.countByStatus("ADOPTED");
    }

    public long getInShelterCount() {
        return animalRepository.countByStatus("AVAILABLE");
    }

    public long getVolunteersCount() {
        return volunteerRepository.count();
    }

    public double getTotalDonationsSum() {
        return donationRepository.findAll()
                .stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }

    @Autowired
    private SupplyRepository supplyRepository;

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public void updateSupply(Long id, Double amount) {
        Supply supply = supplyRepository.findById(id).get();
        supply.setCurrentAmount(amount);
        supplyRepository.save(supply);
    }

    public void saveSupply(Supply supply) {
        supplyRepository.save(supply);
    }

    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }
}