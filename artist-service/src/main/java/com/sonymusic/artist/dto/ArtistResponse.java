package com.sonymusic.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Artist Response DTO
 * 
 * Used for returning artist data to clients.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponse {

    private Long id;
    private String name;
    private String genre;
    private String country;
    private String biography;
    private String imageUrl;
    private String spotifyId;
    private Long monthlyListeners;
    private Boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
