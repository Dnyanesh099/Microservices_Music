package com.sonymusic.search.listener;

import com.sonymusic.search.document.TrackDocument;
import com.sonymusic.search.repository.TrackSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchEventListener {

    private final TrackSearchRepository repository;

    @KafkaListener(topics = "track-events", groupId = "search-group")
    public void handleTrackEvent(Map<String, Object> event) {
        log.info("Received Kafka Event: {}", event);

        if ("TRACK_CREATED".equals(event.get("eventType"))) {
            TrackDocument doc = new TrackDocument();
            doc.setIsrc((String) event.get("isrc"));
            doc.setTitle((String) event.get("title"));
            doc.setAlbumId((String) event.get("albumId")); // Simplified for demo

            repository.save(doc);
            log.info("Indexed Track to Elasticsearch: {}", doc.getIsrc());
        }
    }
}
