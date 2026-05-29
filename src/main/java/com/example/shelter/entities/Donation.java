package com.example.shelter.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter @Setter
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Сумма пожертвования обязательна")
    @DecimalMin(value = "1.0", message = "Сумма пожертвования должна быть не менее 1 рубля")
    private Double amount;

    private LocalDateTime donatedAt;

    @Size(max = 255, message = "Комментарий слишком длинный")
    private String comment;
}