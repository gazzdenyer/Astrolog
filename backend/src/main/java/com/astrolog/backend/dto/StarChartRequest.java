package com.astrolog.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDateTime;

public class StarChartRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Latitude is required")
    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    private Double longitude;

    @NotNull(message = "Observation date/time is required")
    private LocalDateTime observationDateTime;

    @NotBlank(message = "Target is required (constellation, RA/Dec, etc.)")
    private String target;

    private String notes;

    private Boolean downloadImage = false;

    // Constructors
    public StarChartRequest() {
    }

    public StarChartRequest(String location, Double latitude, Double longitude,
                           LocalDateTime observationDateTime, String target) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observationDateTime = observationDateTime;
        this.target = target;
    }

    public StarChartRequest(String location, Double latitude, Double longitude,
                           LocalDateTime observationDateTime, String target, String notes, Boolean downloadImage) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observationDateTime = observationDateTime;
        this.target = target;
        this.notes = notes;
        this.downloadImage = downloadImage;
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getObservationDateTime() {
        return observationDateTime;
    }

    public void setObservationDateTime(LocalDateTime observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getDownloadImage() {
        return downloadImage;
    }

    public void setDownloadImage(Boolean downloadImage) {
        this.downloadImage = downloadImage;
    }
}


