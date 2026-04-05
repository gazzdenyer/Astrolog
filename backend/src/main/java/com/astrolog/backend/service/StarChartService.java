package com.astrolog.backend.service;

import com.astrolog.backend.dto.ObservationDTO;
import com.astrolog.backend.dto.StarChartRequest;
import com.astrolog.backend.dto.StarChartResponse;
import com.astrolog.backend.entity.Observation;
import com.astrolog.backend.repository.ObservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StarChartService {

    private static final Logger log = LoggerFactory.getLogger(StarChartService.class);

    private final AstronomyApiService astronomyApiService;
    private final ImageStorageService imageStorageService;
    private final ObservationRepository observationRepository;

    public StarChartService(AstronomyApiService astronomyApiService,
                           ImageStorageService imageStorageService,
                           ObservationRepository observationRepository) {
        this.astronomyApiService = astronomyApiService;
        this.imageStorageService = imageStorageService;
        this.observationRepository = observationRepository;
    }

    /**
     * Generate a star chart and save observation
     */
    public StarChartResponse generateStarChart(StarChartRequest request) {
        log.info("Generating star chart for location: {}, target: {}",
            request.getLocation(), request.getTarget());

        // Call AstronomyAPI to get the chart URL
        String chartUrl = astronomyApiService.generateStarChart(
            request.getLatitude(),
            request.getLongitude(),
            request.getObservationDateTime(),
            request.getTarget()
        );

        // Create and save observation
        Observation observation = new Observation(
            request.getLocation(),
            request.getLatitude(),
            request.getLongitude(),
            request.getObservationDateTime(),
            request.getTarget(),
            chartUrl,
            request.getNotes()
        );

        // Download and store image if requested
        if (request.getDownloadImage() != null && request.getDownloadImage()) {
            try {
                String storedImagePath = imageStorageService.downloadAndStoreImage(
                    chartUrl,
                    null // observation ID will be set after save
                );
                observation.setLocalImagePath(storedImagePath);
            } catch (Exception e) {
                log.warn("Failed to download image, proceeding with URL only: {}", e.getMessage());
            }
        }

        observation = observationRepository.save(observation);
        log.info("Observation saved with ID: {}", observation.getId());

        return convertToResponse(observation);
    }

    /**
     * Get all observations
     */
    @Transactional(readOnly = true)
    public List<ObservationDTO> getAllObservations() {
        return observationRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get observation by ID
     */
    @Transactional(readOnly = true)
    public ObservationDTO getObservationById(Long id) {
        return observationRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Observation not found with ID: " + id));
    }

    /**
     * Get observations by location
     */
    @Transactional(readOnly = true)
    public List<ObservationDTO> getObservationsByLocation(String location) {
        return observationRepository.findByLocationOrderByCreatedAtDesc(location)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get observations by target
     */
    @Transactional(readOnly = true)
    public List<ObservationDTO> getObservationsByTarget(String target) {
        return observationRepository.findByTargetOrderByCreatedAtDesc(target)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Update observation
     */
    public ObservationDTO updateObservation(Long id, StarChartRequest request) {
        Observation observation = observationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Observation not found with ID: " + id));

        observation.setLocation(request.getLocation());
        observation.setLatitude(request.getLatitude());
        observation.setLongitude(request.getLongitude());
        observation.setObservationDateTime(request.getObservationDateTime());
        observation.setTarget(request.getTarget());
        observation.setNotes(request.getNotes());

        observation = observationRepository.save(observation);
        return convertToDTO(observation);
    }

    /**
     * Delete observation
     */
    public void deleteObservation(Long id) {
        Observation observation = observationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Observation not found with ID: " + id));

        // Delete stored image if exists
        if (observation.getLocalImagePath() != null) {
            imageStorageService.deleteImage(observation.getLocalImagePath());
        }

        observationRepository.delete(observation);
        log.info("Observation deleted with ID: {}", id);
    }

    /**
     * Convert Observation entity to StarChartResponse DTO
     */
    private StarChartResponse convertToResponse(Observation observation) {
        return new StarChartResponse(
            observation.getId(),
            observation.getLocation(),
            observation.getLatitude(),
            observation.getLongitude(),
            observation.getObservationDateTime(),
            observation.getTarget(),
            observation.getChartUrl(),
            observation.getLocalImagePath(),
            observation.getS3ImageUrl(),
            observation.getCreatedAt(),
            observation.getNotes()
        );
    }

    /**
     * Convert Observation entity to ObservationDTO
     */
    private ObservationDTO convertToDTO(Observation observation) {
        return new ObservationDTO(
            observation.getId(),
            observation.getLocation(),
            observation.getLatitude(),
            observation.getLongitude(),
            observation.getObservationDateTime().toString(),
            observation.getTarget(),
            observation.getChartUrl(),
            observation.getLocalImagePath(),
            observation.getS3ImageUrl(),
            observation.getCreatedAt().toString(),
            observation.getNotes()
        );
    }
}

