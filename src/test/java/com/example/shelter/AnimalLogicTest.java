package com.example.shelter;

import com.example.shelter.entities.Animal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalLogicTest {

    @Test
    public void testAnimalData() {
        Animal animal = new Animal();
        animal.setName("Барсик");
        animal.setSpecies("Кот");
        animal.setAge(3);
        animal.setStatus("AVAILABLE");
        animal.setDescription("Милый котик");
        animal.setHealthStatus("Healthy");

        assertEquals("Барсик", animal.getName());
        assertEquals("Кот", animal.getSpecies());
        assertEquals(3, animal.getAge());
        assertEquals("AVAILABLE", animal.getStatus());
        assertEquals("Милый котик", animal.getDescription());
        assertEquals("Healthy", animal.getHealthStatus());
    }
}