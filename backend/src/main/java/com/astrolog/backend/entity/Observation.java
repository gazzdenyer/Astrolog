package com.astrolog.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime observationDateTime;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String chartUrl;

    @Column(name = "local_image_path")
    private String localImagePath;

    @Column(name = "s3_image_url")
    private String s3ImageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "notes")
    private String notes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Observation() {
    }

    public Observation(String location, Double latitude, Double longitude, LocalDateTime observationDateTime,
                       String target, String chartUrl, String notes) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observationDateTime = observationDateTime;
        this.target = target;
        this.chartUrl = chartUrl;
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}


