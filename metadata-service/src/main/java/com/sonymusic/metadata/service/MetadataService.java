package com.sonymusic.metadata.service;

import com.sonymusic.metadata.domain.Track;
import com.sonymusic.metadata.dto.TrackDTO;
import com.sonymusic.metadata.event.TrackCreatedEvent;
import com.sonymusic.metadata.mapper.TrackMapper;
import com.sonymusic.metadata.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataService {

    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public TrackDTO createTrack(TrackDTO trackDTO) {
        log.info("Creating track with ISRC: {}", trackDTO.getIsrc());

        // 1. Validate ISRC Format (CC-XXX-YY-NNNNN)
        if (!trackDTO.getIsrc().matches("^[A-Z]{2}-[A-Z0-9]{3}-\\d{2}-\\d{5}$")) {
            throw new IllegalArgumentException("Invalid ISRC format. Expected: CC-XXX-YY-NNNNN");
        }

        // 2. Check Duplicates
        Optional<Track> existing = trackRepository.findByIsrc(trackDTO.getIsrc());
        if (existing.isPresent()) {
            log.info("Track with ISRC {} already exists. Updating...", trackDTO.getIsrc());
            Track track = existing.get();
            track.setTitle(trackDTO.getTitle());
            track.setDurationSec(trackDTO.getDurationSec());
            track.setUpdatedAt(LocalDateTime.now());
            return trackMapper.toDTO(trackRepository.save(track));
        }

        // 3. Save New Track
        Track track = trackMapper.toEntity(trackDTO);
        track = trackRepository.save(track);

        // 4. Publish Event
        TrackCreatedEvent event = new TrackCreatedEvent("TRACK_CREATED", track.getIsrc(), track.getTitle(), track.getAlbumId());
        kafkaTemplate.send("track-events", track.getIsrc(), event);
        log.info("Published track-created event for ISRC: {}", track.getIsrc());

        return trackMapper.toDTO(track);
    }
    
    @Cacheable(value = "tracks", key = "#isrc")
    public TrackDTO getTrackByIsrc(String isrc) {
        return trackRepository.findByIsrc(isrc)
                .map(trackMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Track not found"));
    }
}
