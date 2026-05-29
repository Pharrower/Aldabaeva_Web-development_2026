package com.example.shelter.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_requests")
@Getter @Setter
public class VolunteerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Выберите тип помощи")
    private String activityType;

    @NotBlank(message = "Расскажите немного о вашем опыте")
    @Size(min = 5, message = "Описание опыта слишком короткое")
    private String experience;

    private LocalDateTime submittedAt;

    private String status; // "NEW", "CONTACTED"
}