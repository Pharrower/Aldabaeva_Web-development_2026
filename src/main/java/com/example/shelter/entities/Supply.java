package com.example.shelter.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplies")
@Getter @Setter
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название ресурса обязательно")
    private String name;

    @NotNull(message = "Текущее количество обязательно")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Double currentAmount;

    @NotNull(message = "Целевое количество обязательно")
    @Positive(message = "Цель должна быть больше нуля")
    private Double targetAmount;

    @NotBlank(message = "Единица измерения обязательна")
    private String unit;

    public int getPercent() {
        if (targetAmount == null || targetAmount == 0) return 100;
        int p = (int) ((currentAmount / targetAmount) * 100);
        return Math.min(p, 100);
    }

    public String getColorClass() {
        int p = getPercent();
        if (p < 30) return "bg-danger";
        if (p < 70) return "bg-warning";
        return "bg-success";
    }
}