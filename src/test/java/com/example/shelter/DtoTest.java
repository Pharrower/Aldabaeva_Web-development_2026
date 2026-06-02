package com.example.shelter;

import com.example.shelter.dto.DonationDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoTest {

    @Test
    public void testDonationDtoGettersAndSetters() {
        DonationDto dto = new DonationDto();

        dto.setAmount(500.0);
        dto.setComment("На корм");
        dto.setStatus("SUCCESS");
        dto.setGateway("Stripe");

        assertEquals(500.0, dto.getAmount());
        assertEquals("На корм", dto.getComment());
        assertEquals("SUCCESS", dto.getStatus());
        assertEquals("Stripe", dto.getGateway());
    }
}