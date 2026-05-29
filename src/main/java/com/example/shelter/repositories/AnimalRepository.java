package com.example.shelter.repositories;

import com.example.shelter.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findBySpeciesAndStatus(String species, String status);
    List<Animal> findByGenderAndStatus(String gender, String status);
    List<Animal> findBySpeciesAndGenderAndStatus(String species, String gender, String status);

    // Поиск только по виду
    List<Animal> findBySpecies(String species);

    List<Animal> findAllByStatus(String status);

    long countByStatus(String status);
}