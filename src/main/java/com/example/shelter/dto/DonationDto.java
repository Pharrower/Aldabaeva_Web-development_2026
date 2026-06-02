package com.example.shelter.dto;

public class DonationDto {
    private Double amount;
    private String comment;
    private String status;
    private String gateway;

    // Геттеры и сеттеры
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }
}