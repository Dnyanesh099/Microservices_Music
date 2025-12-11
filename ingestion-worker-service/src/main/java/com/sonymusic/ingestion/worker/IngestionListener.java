package com.sonymusic.ingestion.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IngestionListener {

    @KafkaListener(topics = "metadata-upload", groupId = "ingestion-group")
    public void processUpload(String fileContent) {
        log.info("Received Bulk Metadata Upload: {}", fileContent);
        
        // Simulating parsing a huge JSON/XML file
        log.info("Parsing XML (StAX Parser)...");
        log.info("Found 150 tracks.");
        
        // In a real scenario, this would call Metadata Service FEIGN client to save tracks
        log.info("Ingestion complete.");
    }
}
