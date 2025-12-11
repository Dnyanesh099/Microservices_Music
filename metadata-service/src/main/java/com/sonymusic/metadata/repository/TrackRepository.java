package com.sonymusic.metadata.repository;

import com.sonymusic.metadata.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {
    Optional<Track> findByIsrc(String isrc);
}
