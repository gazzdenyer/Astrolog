package com.astrolog.backend.dto;

import java.time.LocalDateTime;

public class StarChartResponse {

    private Long observationId;
    private String location;
    private Double latitude;
    private Double longitude;
    private LocalDateTime observationDateTime;
    private String target;
    private String chartUrl;
    private String localImagePath;
    private String s3ImageUrl;
    private LocalDateTime createdAt;
    private String notes;

    public StarChartResponse() {
    }

    public StarChartResponse(Long observationId, String location, Double latitude, Double longitude,
                            LocalDateTime observationDateTime, String target, String chartUrl,
                            String localImagePath, String s3ImageUrl, LocalDateTime createdAt, String notes) {
        this.observationId = observationId;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observationDateTime = observationDateTime;
        this.target = target;
        this.chartUrl = chartUrl;
        this.localImagePath = localImagePath;
        this.s3ImageUrl = s3ImageUrl;
        this.createdAt = createdAt;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getObservationId() { return observationId; }
    public void setObservationId(Long observationId) { this.observationId = observationId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getObservationDateTime() { return observationDateTime; }
    public void setObservationDateTime(LocalDateTime observationDateTime) { this.observationDateTime = observationDateTime; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getChartUrl() { return chartUrl; }
    public void setChartUrl(String chartUrl) { this.chartUrl = chartUrl; }

    public String getLocalImagePath() { return localImagePath; }
    public void setLocalImagePath(String localImagePath) { this.localImagePath = localImagePath; }

    public String getS3ImageUrl() { return s3ImageUrl; }
    public void setS3ImageUrl(String s3ImageUrl) { this.s3ImageUrl = s3ImageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}


