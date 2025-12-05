package com.sonymusic.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artist Request DTO
 * 
 * Used for creating and updating artists.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequest {

    @NotBlank(message = "Artist name is required")
    @Size(min = 1, max = 255, message = "Artist name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Genre is required")
    @Size(max = 100)
    private String genre;

    @Size(max = 100)
    private String country;

    private String biography;

    private String imageUrl;

    private String spotifyId;

    private Long monthlyListeners;

    private Boolean verified;
}
