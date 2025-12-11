package com.sonymusic.metadata.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackCreatedEvent {
    private String eventType = "TRACK_CREATED";
    private String isrc;
    private String title;
    private UUID albumId;
}
