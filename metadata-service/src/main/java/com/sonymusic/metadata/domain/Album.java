package com.sonymusic.metadata.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String label;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "artist_id", nullable = false)
    private UUID artistId;
}
