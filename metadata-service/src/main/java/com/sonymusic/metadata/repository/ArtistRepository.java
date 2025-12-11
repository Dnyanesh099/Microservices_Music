package com.sonymusic.metadata.repository;

import com.sonymusic.metadata.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
}
