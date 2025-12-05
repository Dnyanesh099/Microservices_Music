package com.sonymusic.artist.controller;

import com.sonymusic.artist.dto.ArtistRequest;
import com.sonymusic.artist.dto.ArtistResponse;
import com.sonymusic.artist.service.ArtistService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Artist Controller
 * 
 * REST API endpoints for artist management.
 */
@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    @CircuitBreaker(name = "artistService", fallbackMethod = "getAllArtistsFallback")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        log.info("GET /api/artists - Fetching all artists");
        List<ArtistResponse> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "artistService", fallbackMethod = "getArtistByIdFallback")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        log.info("GET /api/artists/{} - Fetching artist by id", id);
        ArtistResponse artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@Valid @RequestBody ArtistRequest request) {
        log.info("POST /api/artists - Creating new artist: {}", request.getName());
        ArtistResponse artist = artistService.createArtist(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody ArtistRequest request) {
        log.info("PUT /api/artists/{} - Updating artist", id);
        ArtistResponse artist = artistService.updateArtist(id, request);
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        log.info("DELETE /api/artists/{} - Deleting artist", id);
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtists(@RequestParam String q) {
        log.info("GET /api/artists/search?q={} - Searching artists", q);
        List<ArtistResponse> artists = artistService.searchArtists(q);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<ArtistResponse>> getArtistsByGenre(@PathVariable String genre) {
        log.info("GET /api/artists/genre/{} - Fetching artists by genre", genre);
        List<ArtistResponse> artists = artistService.getArtistsByGenre(genre);
        return ResponseEntity.ok(artists);
    }

    // Fallback methods for circuit breaker
    public ResponseEntity<List<ArtistResponse>> getAllArtistsFallback(Exception ex) {
        log.error("Circuit breaker activated for getAllArtists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    public ResponseEntity<ArtistResponse> getArtistByIdFallback(Long id, Exception ex) {
        log.error("Circuit breaker activated for getArtistById: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
