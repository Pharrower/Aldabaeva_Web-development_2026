package com.example.shelter.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя питомца не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @NotBlank(message = "Вид животного обязателен")
    private String species; // кошка или собака

    private String gender;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 30, message = "Возраст животного слишком большой")
    private Integer age;

    @Positive(message = "Вес должен быть больше нуля")
    private Double weight;

    @Column(length = 1000)
    private String healthStatus;

    @Column(length = 2000)
    private String description;

    private String imageUrl;

    private String status = "AVAILABLE";
}