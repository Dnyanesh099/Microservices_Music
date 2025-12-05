package com.sonymusic.artist.repository;

import com.sonymusic.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Artist Repository
 * 
 * Data access layer for Artist entity.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByName(String name);

    List<Artist> findByGenre(String genre);

    List<Artist> findByCountry(String country);

    Optional<Artist> findBySpotifyId(String spotifyId);

    @Query("SELECT a FROM Artist a WHERE a.verified = true ORDER BY a.monthlyListeners DESC")
    List<Artist> findTopVerifiedArtists();

    @Query("SELECT a FROM Artist a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Artist> searchByName(String searchTerm);
}
