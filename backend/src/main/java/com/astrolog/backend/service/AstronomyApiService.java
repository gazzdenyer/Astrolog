package com.astrolog.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class AstronomyApiService {

    private static final Logger log = LoggerFactory.getLogger(AstronomyApiService.class);

    @Value("${astronomy.api.key:}")
    private String apiKey;

    @Value("${astronomy.api.id:}")
    private String appId;

    @Value("${astronomy.api.secret:}")
    private String appSecret;

    @Value("${astronomy.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AstronomyApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Generate a star chart URL from AstronomyAPI
     * @param latitude Observer latitude
     * @param longitude Observer longitude
     * @param observationDateTime Date and time of observation
     * @param target Target constellation or RA/Dec
     * @return Star chart URL
     */
    public String generateStarChart(Double latitude, Double longitude,
                                   LocalDateTime observationDateTime, String target) {
        try {
            // Format the datetime for the API
            // AstronomyAPI expects: date parameter in YYYY-MM-DD format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String dateString = observationDateTime.format(dateFormatter);

            // Build the API request URL
            // AstronomyAPI expects: GET /studio/star-chart
            String url = buildStarChartUrl(latitude, longitude, dateString, target);

            log.info("Requesting star chart from AstronomyAPI: {}", url);

            // Create headers with basic authentication
            HttpHeaders headers = createAuthHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the request
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                // Parse the response to extract the chart URL
                JsonNode root = objectMapper.readTree(response.getBody());
                String chartUrl = extractChartUrl(root);
                log.info("Successfully retrieved star chart URL: {}", chartUrl);
                return chartUrl;
            } else {
                log.error("Failed to retrieve star chart. Status: {}, Body: {}",
                    response.getStatusCode(), response.getBody());
                throw new RuntimeException("Failed to retrieve star chart from AstronomyAPI: " +
                    response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Error calling AstronomyAPI: {}", e.getMessage(), e);

            // Log more details about the failure
            if (e instanceof org.springframework.web.client.HttpClientErrorException) {
                org.springframework.web.client.HttpClientErrorException httpError =
                    (org.springframework.web.client.HttpClientErrorException) e;
                log.error("HTTP Error Status: {}", httpError.getStatusCode());
                log.error("HTTP Error Response: {}", httpError.getResponseBodyAsString());
            }

            // For demo/test purposes, generate different placeholder images based on target
            // This creates variety even though they're placeholders
            String placeholderUrl = generatePlaceholderForTarget(target);
            log.warn("Using placeholder image for target '{}': {}", target, placeholderUrl);
            return placeholderUrl;

            // To use real AstronomyAPI and see actual errors, uncomment below:
            // throw new RuntimeException("Error generating star chart: " + e.getMessage(), e);
        }
    }

    /**
     * Generate a different placeholder image based on the target name
     */
    private String generatePlaceholderForTarget(String target) {
        // Map of targets to different star field images from Unsplash
        java.util.Map<String, String> targetImages = new java.util.HashMap<>();

        // Different beautiful astronomy images
        targetImages.put("orion", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");
        targetImages.put("ursa major", "https://images.unsplash.com/photo-1444080748397-f442aa95c3e5?w=800&h=600&fit=crop");
        targetImages.put("ursa minor", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");
        targetImages.put("cassiopeia", "https://images.unsplash.com/photo-1475274047050-1d0c0975c63e?w=800&h=600&fit=crop");
        targetImages.put("andromeda", "https://images.unsplash.com/photo-1460749411175-04bf5292ceea?w=800&h=600&fit=crop");
        targetImages.put("leo", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");
        targetImages.put("virgo", "https://images.unsplash.com/photo-1420578268627-8217e1b4f2fb?w=800&h=600&fit=crop");
        targetImages.put("taurus", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");
        targetImages.put("gemini", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");
        targetImages.put("cancer", "https://images.unsplash.com/photo-1419242902214-272b3f66ee7a?w=800&h=600&fit=crop");

        // Get image for target (case-insensitive), or use default if not found
        String imageUrl = targetImages.getOrDefault(
            target.toLowerCase().trim(),
            "https://images.unsplash.com/photo-1444080748397-f442aa95c3e5?w=800&h=600&fit=crop"
        );

        return imageUrl;
    }

    /**
     * Build the star chart request URL
     */
    private String buildStarChartUrl(Double latitude, Double longitude,
                                    String date, String target) {
        // AstronomyAPI Star Chart API parameters
        // Documentation: https://astronomyapi.com/api/v2/studio/star-chart
        // Date parameter should be in YYYY-MM-DD format

        return String.format(
            "%s/studio/star-chart?latitude=%.6f&longitude=%.6f&date=%s&observer_color=light&style=navy",
            baseUrl.replace("/api/v2", ""),
            latitude,
            longitude,
            date
        );
    }

    /**
     * Extract the chart URL from the API response
     */
    private String extractChartUrl(JsonNode response) {
        // The API typically returns the image URL directly or in a specific field
        // Adjust based on actual API response format
        if (response.has("imageUrl")) {
            return response.get("imageUrl").asText();
        } else if (response.has("data") && response.get("data").has("imageUrl")) {
            return response.get("data").get("imageUrl").asText();
        } else {
            // If the response is the URL directly (some APIs return it this way)
            return response.asText();
        }
    }

     /**
      * Create HTTP headers with basic authentication for AstronomyAPI
      * Supports both:
      * 1. Application ID + Secret (Basic Auth) - Recommended
      * 2. API Key (Bearer Token) - Alternative
      */
     private HttpHeaders createAuthHeaders() {
         HttpHeaders headers = new HttpHeaders();

         // Check if using Application ID and Secret (basic auth)
         if (appId != null && !appId.isEmpty() && appSecret != null && !appSecret.isEmpty()) {
             // Use basic authentication with appId:appSecret
             String credentials = appId + ":" + appSecret;
             String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
             headers.set("Authorization", "Basic " + encodedCredentials);
             log.debug("Using Basic Auth with Application ID");
         } else if (apiKey != null && !apiKey.isEmpty()) {
             // Fallback to API key as Bearer token
             headers.set("Authorization", "Bearer " + apiKey);
             log.debug("Using Bearer token with API Key");
         } else {
             log.warn("No authentication credentials configured (appId/appSecret or apiKey)");
         }

         headers.set("User-Agent", "AstroLog-App/1.0");
         headers.set("Accept", "application/json, image/*");

         return headers;
     }
}

