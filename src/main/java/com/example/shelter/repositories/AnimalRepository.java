package com.example.shelter.repositories;

import com.example.shelter.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findBySpeciesAndStatus(String species, String status);
    List<Animal> findByGenderAndStatus(String gender, String status);
    List<Animal> findBySpeciesAndGenderAndStatus(String species, String gender, String status);

    List<Animal> findBySpecies(String species);

    List<Animal> findAllByStatus(String status);

    long countByStatus(String status);

    @Query("SELECT a FROM Animal a WHERE " +
            "(:species IS NULL OR a.species = :species) AND " +
            "(:gender IS NULL OR a.gender = :gender) AND " +
            "a.status = 'AVAILABLE'")
    List<Animal> findFilteredAnimals(@Param("species") String species,
                                     @Param("gender") String gender);
}