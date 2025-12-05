package com.sonymusic.artist.service;

import com.sonymusic.artist.dto.ArtistRequest;
import com.sonymusic.artist.dto.ArtistResponse;
import com.sonymusic.artist.entity.Artist;
import com.sonymusic.artist.event.ArtistEvent;
import com.sonymusic.artist.exception.ArtistNotFoundException;
import com.sonymusic.artist.mapper.ArtistMapper;
import com.sonymusic.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Artist Service
 * 
 * Business logic for artist management.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final KafkaTemplate<String, ArtistEvent> kafkaTemplate;
    
    private static final String ARTIST_TOPIC = "artist-events";

    public List<ArtistResponse> getAllArtists() {
        log.info("Fetching all artists");
        return artistRepository.findAll()
                .stream()
                .map(artistMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ArtistResponse getArtistById(Long id) {
        log.info("Fetching artist with id: {}", id);
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        return artistMapper.toResponse(artist);
    }

    public ArtistResponse createArtist(ArtistRequest request) {
        log.info("Creating new artist: {}", request.getName());
        
        Artist artist = artistMapper.toEntity(request);
        Artist savedArtist = artistRepository.save(artist);
        
        // Publish event to Kafka
        publishArtistEvent(savedArtist, "CREATED");
        
        log.info("Artist created successfully with id: {}", savedArtist.getId());
        return artistMapper.toResponse(savedArtist);
    }

    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        log.info("Updating artist with id: {}", id);
        
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        
        artistMapper.updateEntityFromRequest(request, artist);
        Artist updatedArtist = artistRepository.save(artist);
        
        // Publish event to Kafka
        publishArtistEvent(updatedArtist, "UPDATED");
        
        log.info("Artist updated successfully with id: {}", id);
        return artistMapper.toResponse(updatedArtist);
    }

    public void deleteArtist(Long id) {
        log.info("Deleting artist with id: {}", id);
        
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id: " + id));
        
        artistRepository.delete(artist);
        
        // Publish event to Kafka
        publishArtistEvent(artist, "DELETED");
        
        log.info("Artist deleted successfully with id: {}", id);
    }

    public List<ArtistResponse> searchArtists(String searchTerm) {
        log.info("Searching artists with term: {}", searchTerm);
        return artistRepository.searchByName(searchTerm)
                .stream()
                .map(artistMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ArtistResponse> getArtistsByGenre(String genre) {
        log.info("Fetching artists by genre: {}", genre);
        return artistRepository.findByGenre(genre)
                .stream()
                .map(artistMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void publishArtistEvent(Artist artist, String eventType) {
        ArtistEvent event = ArtistEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .artistId(artist.getId())
                .artistName(artist.getName())
                .genre(artist.getGenre())
                .country(artist.getCountry())
                .timestamp(LocalDateTime.now())
                .source("artist-service")
                .build();
        
        kafkaTemplate.send(ARTIST_TOPIC, event);
        log.info("Published {} event for artist: {}", eventType, artist.getName());
    }
}
