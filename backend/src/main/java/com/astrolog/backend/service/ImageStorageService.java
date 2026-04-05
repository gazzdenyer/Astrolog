package com.astrolog.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final Logger log = LoggerFactory.getLogger(ImageStorageService.class);

    @Value("${storage.type:local}")
    private String storageType;

    @Value("${storage.local.path:./uploads}")
    private String localStoragePath;

    public ImageStorageService() {
    }

    /**
     * Download and store the star chart image
     * @param imageUrl URL of the image to download
     * @param observationId ID of the observation
     * @return Path or URL where the image is stored
     */
    public String downloadAndStoreImage(String imageUrl, Long observationId) {
        try {
            if ("local".equals(storageType)) {
                return downloadToLocalStorage(imageUrl, observationId);
            } else if ("s3".equals(storageType)) {
                return downloadToS3(imageUrl, observationId);
            } else {
                log.warn("Unknown storage type: {}", storageType);
                return imageUrl; // Return original URL if storage is not configured
            }
        } catch (Exception e) {
            log.error("Error downloading and storing image: {}", imageUrl, e);
            throw new RuntimeException("Failed to download and store image", e);
        }
    }

    /**
     * Download image to local storage
     */
    private String downloadToLocalStorage(String imageUrl, Long observationId) throws IOException {
        // Create storage directory if it doesn't exist
        Path storagePath = Paths.get(localStoragePath);
        Files.createDirectories(storagePath);

        // Generate filename
        String filename = generateFilename(observationId);
        Path filepath = storagePath.resolve(filename);

        // Download the image
        log.info("Downloading image from: {}", imageUrl);
        URL url = new URL(imageUrl);
        byte[] imageBytes = url.openStream().readAllBytes();

        // Save to local storage
        Files.write(filepath, imageBytes);
        log.info("Image saved to: {}", filepath);

        return filepath.toString();
    }

    /**
     * Download image to S3 storage
     * (Implementation placeholder - requires AWS SDK configuration)
     */
    private String downloadToS3(String imageUrl, Long observationId) throws IOException {
        log.warn("S3 storage not yet implemented. Please configure local storage or implement S3 integration.");
        // TODO: Implement S3 download and storage
        // This would require:
        // 1. AWS S3 client configuration
        // 2. Bucket credentials and configuration
        // 3. Upload logic
        return imageUrl; // Return original URL for now
    }

    /**
     * Generate a unique filename for the stored image
     */
    private String generateFilename(Long observationId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return String.format("chart_%d_%s_%s.png", observationId, date, uniqueId);
    }

    /**
     * Delete stored image
     */
    public void deleteImage(String imagePath) {
        try {
            if ("local".equals(storageType) && imagePath != null) {
                Path path = Paths.get(imagePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                    log.info("Image deleted: {}", imagePath);
                }
            }
        } catch (IOException e) {
            log.error("Error deleting image: {}", imagePath, e);
        }
    }
}

