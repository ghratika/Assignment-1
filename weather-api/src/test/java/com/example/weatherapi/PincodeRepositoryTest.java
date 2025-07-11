package com.example.weatherapi;

import com.example.weatherapi.entity.PincodeInfo;
import com.example.weatherapi.repository.PincodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PincodeRepositoryTest {

    @Autowired
    private PincodeRepository pincodeRepo;

    @Test
    void testSaveAndFindPincode() {
        PincodeInfo pincodeInfo = PincodeInfo.builder()
                .pincode("110001")
                .latitude(28.6)
                .longitude(77.2)
                .build();

        pincodeRepo.save(pincodeInfo);

        Optional<PincodeInfo> found = pincodeRepo.findById("110001");
        assertTrue(found.isPresent());
        assertEquals(28.6, found.get().getLatitude());
    }
}
