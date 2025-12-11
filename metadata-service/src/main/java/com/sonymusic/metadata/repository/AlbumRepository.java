package com.sonymusic.metadata.repository;

import com.sonymusic.metadata.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
}
