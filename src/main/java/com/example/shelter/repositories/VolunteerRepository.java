package com.example.shelter.repositories;

import com.example.shelter.entities.VolunteerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerRequest, Long> {
    // Здесь JpaRepository сам создаст методы:
    // .save() для сохранения,
    // .findAll() для получения списка всех волонтеров
}