package com.example.shelter;

import com.example.shelter.entities.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class FullEntityTest {

    @Test
    public void testAllEntities() {
        // 1. Тест User
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("hash");
        user.setFirstName("Ivan");
        user.setRole("ROLE_USER");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("Ivan", user.getFirstName());
        assertEquals("ROLE_USER", user.getRole());

        // 2. Тест Animal
        Animal animal = new Animal();
        animal.setId(10L);
        animal.setName("Бобик");
        animal.setSpecies("Собака");
        animal.setAge(5);
        animal.setStatus("AVAILABLE");
        animal.setHealthStatus("Healthy");
        animal.setDescription("Friendly dog");

        assertEquals("Бобик", animal.getName());
        assertEquals("AVAILABLE", animal.getStatus());
        assertEquals(5, animal.getAge());

        // 3. Тест Supply (включая логику процентов и цветов)
        Supply supply = new Supply();
        supply.setName("Корм");
        supply.setTargetAmount(100.0);

        // Проверка красного статуса (10%)
        supply.setCurrentAmount(10.0);
        assertEquals(10, supply.getPercent());
        assertEquals("bg-danger", supply.getColorClass());

        // Проверка желтого статуса (50%)
        supply.setCurrentAmount(50.0);
        assertEquals(50, supply.getPercent());
        assertEquals("bg-warning", supply.getColorClass());

        // Проверка зеленого статуса (90%)
        supply.setCurrentAmount(90.0);
        assertEquals(90, supply.getPercent());
        assertEquals("bg-success", supply.getColorClass());

        // 4. Тест AdoptionRequest
        AdoptionRequest request = new AdoptionRequest();
        request.setId(1L);
        request.setUser(user);
        request.setAnimal(animal);
        request.setStatus("PENDING");
        request.setRequestDate(LocalDateTime.now());

        assertNotNull(request.getUser());
        assertEquals("PENDING", request.getStatus());
        assertEquals("Бобик", request.getAnimal().getName());

        // 5. Тест Donation
        Donation donation = new Donation();
        donation.setAmount(1000.0);
        donation.setUser(user);
        donation.setDonatedAt(LocalDateTime.now());

        assertEquals(1000.0, donation.getAmount());
        assertNotNull(donation.getDonatedAt());

        // 6. Тест VolunteerRequest
        VolunteerRequest volRequest = new VolunteerRequest();
        volRequest.setActivityType("Выгул");
        volRequest.setUser(user);
        volRequest.setStatus("NEW");

        assertEquals("Выгул", volRequest.getActivityType());
        assertEquals("NEW", volRequest.getStatus());
    }
    @Test
    public void testAdditionalMethods() {
        // Проверка конструктора Animal со всеми параметрами (AllArgsConstructor)
        Animal allArgsAnimal = new Animal(1L, "Барсик", "Кот", "М", 2, 4.5, "Здоров", "Милый", "url", "AVAILABLE");
        assertNotNull(allArgsAnimal.getName());

        // Проверка сеттеров, которые мы еще не вызывали
        Donation d = new Donation();
        d.setComment("На еду");
        assertEquals("На еду", d.getComment());

        AdoptionRequest ar = new AdoptionRequest();
        ar.setMessage("Хочу кота");
        assertEquals("Хочу кота", ar.getMessage());

        VolunteerRequest vr = new VolunteerRequest();
        vr.setExperience("3 года");
        vr.setSubmittedAt(LocalDateTime.now());
        assertNotNull(vr.getSubmittedAt());
    }
}