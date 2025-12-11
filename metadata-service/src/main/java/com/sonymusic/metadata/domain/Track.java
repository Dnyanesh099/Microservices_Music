package com.sonymusic.metadata.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String isrc;

    @Column(name = "duration_sec")
    private Integer durationSec;

    @Column(name = "album_id", nullable = false)
    private UUID albumId;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
