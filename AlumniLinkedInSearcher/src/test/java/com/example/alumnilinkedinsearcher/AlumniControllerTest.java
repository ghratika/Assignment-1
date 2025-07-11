
package com.example.alumnilinkedinsearcher;

import com.example.alumnilinkedinsearcher.model.Alumni;
import com.example.alumnilinkedinsearcher.service.AlumniService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class AlumniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumniService alumniService;

    @Test
    void getAllAlumni_shouldReturnAlumniList() throws Exception {
        List<Alumni> mockAlumni = List.of(
                new Alumni("John Doe", "Software Engineer", "University of XYZ", "New York, NY", "Headline", 2020)
        );
        given(alumniService.getAllAlumni()).willReturn(mockAlumni);

        mockMvc.perform(get("/api/alumni/all").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("success"))
               .andExpect(jsonPath("$.data[0].name").value("John Doe"));
    }
}
