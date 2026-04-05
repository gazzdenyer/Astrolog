package com.astrolog.backend.controller;

import com.astrolog.backend.dto.ObservationDTO;
import com.astrolog.backend.dto.StarChartRequest;
import com.astrolog.backend.dto.StarChartResponse;
import com.astrolog.backend.service.StarChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/star-charts")
@Validated
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class StarChartController {

    private static final Logger log = LoggerFactory.getLogger(StarChartController.class);

    private final StarChartService starChartService;

    public StarChartController(StarChartService starChartService) {
        this.starChartService = starChartService;
    }

    /**
     * Generate a new star chart
     * POST /api/star-charts/generate
     */
    @PostMapping("/generate")
    public ResponseEntity<StarChartResponse> generateStarChart(@Valid @RequestBody StarChartRequest request) {
        log.info("Received request to generate star chart for location: {}", request.getLocation());
        try {
            StarChartResponse response = starChartService.generateStarChart(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error generating star chart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all observations
     * GET /api/star-charts/observations
     */
    @GetMapping("/observations")
    public ResponseEntity<List<ObservationDTO>> getAllObservations() {
        log.info("Fetching all observations");
        try {
            List<ObservationDTO> observations = starChartService.getAllObservations();
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            log.error("Error fetching observations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get observation by ID
     * GET /api/star-charts/observations/{id}
     */
    @GetMapping("/observations/{id}")
    public ResponseEntity<ObservationDTO> getObservationById(@PathVariable Long id) {
        log.info("Fetching observation with ID: {}", id);
        try {
            ObservationDTO observation = starChartService.getObservationById(id);
            return ResponseEntity.ok(observation);
        } catch (RuntimeException e) {
            log.warn("Observation not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error fetching observation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get observations by location
     * GET /api/star-charts/observations/location/{location}
     */
    @GetMapping("/observations/location/{location}")
    public ResponseEntity<List<ObservationDTO>> getObservationsByLocation(@PathVariable String location) {
        log.info("Fetching observations for location: {}", location);
        try {
            List<ObservationDTO> observations = starChartService.getObservationsByLocation(location);
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            log.error("Error fetching observations by location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get observations by target
     * GET /api/star-charts/observations/target/{target}
     */
    @GetMapping("/observations/target/{target}")
    public ResponseEntity<List<ObservationDTO>> getObservationsByTarget(@PathVariable String target) {
        log.info("Fetching observations for target: {}", target);
        try {
            List<ObservationDTO> observations = starChartService.getObservationsByTarget(target);
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            log.error("Error fetching observations by target", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update observation
     * PUT /api/star-charts/observations/{id}
     */
    @PutMapping("/observations/{id}")
    public ResponseEntity<ObservationDTO> updateObservation(@PathVariable Long id,
                                                           @Valid @RequestBody StarChartRequest request) {
        log.info("Updating observation with ID: {}", id);
        try {
            ObservationDTO observation = starChartService.updateObservation(id, request);
            return ResponseEntity.ok(observation);
        } catch (RuntimeException e) {
            log.warn("Observation not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating observation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Delete observation
     * DELETE /api/star-charts/observations/{id}
     */
    @DeleteMapping("/observations/{id}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long id) {
        log.info("Deleting observation with ID: {}", id);
        try {
            starChartService.deleteObservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Observation not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error deleting observation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     * GET /api/star-charts/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AstroLog API is running");
    }
}

