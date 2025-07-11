
package com.example.alumnilinkedinsearcher.controller;

import com.example.alumnilinkedinsearcher.model.Alumni;
import com.example.alumnilinkedinsearcher.service.AlumniService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {

    private final AlumniService alumniService;

    public AlumniController(AlumniService alumniService) {
        this.alumniService = alumniService;
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> searchAlumni(@RequestBody Map<String, Object> request) {
        String university = (String) request.get("university");
        String designation = (String) request.get("designation");
        Integer passoutYear = request.get("passoutYear") != null ? (Integer) request.get("passoutYear") : null;

        List<Alumni> alumniList = alumniService.searchAndSaveAlumni(university, designation, passoutYear);

        return ResponseEntity.ok(Map.of("status", "success", "data", alumniList));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllAlumni() {
        List<Alumni> alumniList = alumniService.getAllAlumni();
        return ResponseEntity.ok(Map.of("status", "success", "data", alumniList));
    }
}
