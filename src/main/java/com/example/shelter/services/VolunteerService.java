package com.example.shelter.services;

import com.example.shelter.dto.AdoptionRequestDto;
import com.example.shelter.entities.User;
import com.example.shelter.entities.VolunteerRequest;
import com.example.shelter.repositories.VolunteerRepository;
import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void saveVolunteerRequest(VolunteerRequest request, User user) {
        request.setUser(user);
        request.setSubmittedAt(LocalDateTime.now());
        request.setStatus("NEW");
        volunteerRepository.save(request);
    }

    public List<VolunteerRequest> getAllVolunteerRequests() { return volunteerRepository.findAll(); }

    public long getVolunteersCount() { return volunteerRepository.count(); }
}