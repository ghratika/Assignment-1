
package com.example.alumnilinkedinsearcher.service;

import com.example.alumnilinkedinsearcher.model.Alumni;
import com.example.alumnilinkedinsearcher.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class AlumniService {

    private final AlumniRepository alumniRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${phantombuster.api.key}")
    private String phantomBusterApiKey;

    public AlumniService(AlumniRepository alumniRepository) {
        this.alumniRepository = alumniRepository;
    }

    public List<Alumni> searchAndSaveAlumni(String university, String designation, Integer passoutYear) {
        String apiUrl = "https://api.phantombuster.com/api/v2/agent/launch";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Phantombuster-Key-1", phantomBusterApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("university", university);
        body.put("designation", designation);
        if (passoutYear != null) {
            body.put("passoutYear", passoutYear);
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);

        // Here we mock response parsing since actual API response structure depends on PhantomBuster
        List<Alumni> alumniList = Arrays.asList(
            new Alumni("John Doe", "Software Engineer", university, "New York, NY", "Passionate Software Engineer at XYZ Corp", 2020),
            new Alumni("Jane Smith", "Data Scientist", university, "San Francisco, CA", "Data Scientist | AI Enthusiast", 2019)
        );

        alumniRepository.saveAll(alumniList);
        return alumniList;
    }

    public List<Alumni> getAllAlumni() {
        return alumniRepository.findAll();
    }
}
