package org.example.controller;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testMutantEndpoint_ReturnOk() throws Exception {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isOk());
    }

    @Test
    void testHumanEndpoint_ReturnForbidden() throws Exception {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInvalidDna_ReturnBadRequest() throws Exception {
        String[] dna = { "ATGX", "CAGT" };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNonSquareDna_ReturnBadRequest() throws Exception {
        String[] dna = { "ATGC", "CAG" };
        DnaRequest request = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testStatsEndpoint_ReturnOk() throws Exception {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.ratio").exists());
    }

    @Test
    void testMethodNotAllowed_Return405() throws Exception {
        mockMvc.perform(get("/mutant"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void testEmptyBody_ReturnBadRequest() throws Exception {
        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNullDna_ReturnBadRequest() throws Exception {
        String json = "{\"dna\": null}";
        mockMvc.perform(post("/mutant")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
