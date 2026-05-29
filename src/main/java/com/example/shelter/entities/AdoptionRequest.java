package com.example.shelter.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_requests")
@Getter @Setter
public class AdoptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private String status; // "PENDING", "APPROVED", "REJECTED"

    private LocalDateTime requestDate;

    @NotBlank(message = "Пожалуйста, напишите, почему вы хотите забрать питомца")
    @Size(min = 10, max = 1000, message = "Сообщение должно быть от 10 до 1000 символов")
    private String message;
}