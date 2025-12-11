CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE artists (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    spotify_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE albums (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    label VARCHAR(255),
    release_date DATE,
    artist_id UUID NOT NULL REFERENCES artists(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tracks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    isrc VARCHAR(20) NOT NULL UNIQUE,
    duration_sec INT,
    album_id UUID NOT NULL REFERENCES albums(id),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed Data --
INSERT INTO artists (name, spotify_id) VALUES ('Taylor Swift', '06HL4z0CvFAsei5YNgXkP4');
