package com.astrolog.backend.dto;

public class ObservationDTO {

    private Long id;
    private String location;
    private Double latitude;
    private Double longitude;
    private String observationDateTime;
    private String target;
    private String chartUrl;
    private String localImagePath;
    private String s3ImageUrl;
    private String createdAt;
    private String notes;

    public ObservationDTO() {
    }

    public ObservationDTO(Long id, String location, Double latitude, Double longitude,
                         String observationDateTime, String target, String chartUrl,
                         String localImagePath, String s3ImageUrl, String createdAt, String notes) {
        this.id = id;
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getObservationDateTime() { return observationDateTime; }
    public void setObservationDateTime(String observationDateTime) { this.observationDateTime = observationDateTime; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getChartUrl() { return chartUrl; }
    public void setChartUrl(String chartUrl) { this.chartUrl = chartUrl; }

    public String getLocalImagePath() { return localImagePath; }
    public void setLocalImagePath(String localImagePath) { this.localImagePath = localImagePath; }

    public String getS3ImageUrl() { return s3ImageUrl; }
    public void setS3ImageUrl(String s3ImageUrl) { this.s3ImageUrl = s3ImageUrl; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}


