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

        // Проверяем, что данные сохраняются и возвращаются корректно
        assertEquals("Барсик", animal.getName());
        assertEquals("Кот", animal.getSpecies());
        assertEquals(3, animal.getAge());
    }
}