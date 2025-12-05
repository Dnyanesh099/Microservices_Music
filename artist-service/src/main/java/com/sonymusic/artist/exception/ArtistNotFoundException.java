package com.sonymusic.artist.exception;

/**
 * Artist Not Found Exception
 * 
 * Thrown when an artist is not found in the database.
 */
public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(String message) {
        super(message);
    }
}
