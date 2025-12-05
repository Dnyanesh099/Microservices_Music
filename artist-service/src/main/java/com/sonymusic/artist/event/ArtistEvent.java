package com.sonymusic.artist.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Artist Event
 * 
 * Event published to Kafka when artist operations occur.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistEvent {

    private String eventId;
    private String eventType; // CREATED, UPDATED, DELETED
    private Long artistId;
    private String artistName;
    private String genre;
    private String country;
    private LocalDateTime timestamp;
    private String source;
}
