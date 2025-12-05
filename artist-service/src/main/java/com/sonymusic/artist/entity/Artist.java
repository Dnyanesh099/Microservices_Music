package com.sonymusic.artist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Artist Entity
 * 
 * Represents an artist in the Sony Music catalog.
 */
@Entity
@Table(name = "artists", indexes = {
    @Index(name = "idx_artist_name", columnList = "name"),
    @Index(name = "idx_artist_genre", columnList = "genre")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Artist name is required")
    @Size(min = 1, max = 255, message = "Artist name must be between 1 and 255 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Genre is required")
    @Size(max = 100)
    @Column(nullable = false)
    private String genre;

    @Size(max = 100)
    private String country;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "spotify_id", unique = true)
    private String spotifyId;

    @Column(name = "monthly_listeners")
    private Long monthlyListeners;

    @Column(name = "verified")
    private Boolean verified = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
