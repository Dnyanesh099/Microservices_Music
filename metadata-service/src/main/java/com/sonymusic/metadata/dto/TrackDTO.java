package com.sonymusic.metadata.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TrackDTO {
    private UUID id;
    private String title;
    private String isrc;
    private Integer durationSec;
    private UUID albumId;
}
