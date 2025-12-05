-- Create artists table
CREATE TABLE IF NOT EXISTS artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    country VARCHAR(100),
    biography TEXT,
    image_url VARCHAR(500),
    spotify_id VARCHAR(100) UNIQUE,
    monthly_listeners BIGINT DEFAULT 0,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_artist_name ON artists(name);
CREATE INDEX idx_artist_genre ON artists(genre);
CREATE INDEX idx_artist_country ON artists(country);
CREATE INDEX idx_artist_verified ON artists(verified);

-- Insert sample data
INSERT INTO artists (name, genre, country, biography, monthly_listeners, verified) VALUES
('Taylor Swift', 'Pop', 'USA', 'American singer-songwriter known for narrative songs about her personal life.', 95000000, true),
('The Weeknd', 'R&B', 'Canada', 'Canadian singer, songwriter, and record producer.', 110000000, true),
('Ed Sheeran', 'Pop', 'UK', 'English singer-songwriter known for his acoustic pop songs.', 85000000, true),
('Ariana Grande', 'Pop', 'USA', 'American singer, songwriter, and actress.', 80000000, true),
('Drake', 'Hip Hop', 'Canada', 'Canadian rapper, singer, and songwriter.', 75000000, true);
