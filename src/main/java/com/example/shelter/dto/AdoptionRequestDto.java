package com.example.shelter.dto;

import lombok.Data;

@Data // Аннотация Lombok для автоматической генерации геттеров, сеттеров и конструкторов
public class AdoptionRequestDto {
    private Long animalId;       // ID питомца, которого хотят забрать
    private String applicantName; // Имя того, кто подает заявку
    private String phone;         // Контактный телефон
    private String message;       // Дополнительное сообщение
}