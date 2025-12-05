-- Create albums table
CREATE TABLE IF NOT EXISTS albums (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist_id BIGINT NOT NULL,
    release_date DATE,
    genre VARCHAR(100),
    label VARCHAR(255),
    cover_art_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create tracks table
CREATE TABLE IF NOT EXISTS tracks (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL REFERENCES albums(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    duration_seconds INTEGER,
    track_number INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_album_artist ON albums(artist_id);
CREATE INDEX idx_track_album ON tracks(album_id);

-- Sample data
INSERT INTO albums (title, artist_id, release_date, genre, label) VALUES
('1989', 1, '2014-10-27', 'Pop', 'Big Machine Records'),
('After Hours', 2, '2020-03-20', 'R&B', 'XO Records'),
('÷ (Divide)', 3, '2017-03-03', 'Pop', 'Asylum Records');
