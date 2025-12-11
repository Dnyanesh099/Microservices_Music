package com.sonymusic.search.repository;

import com.sonymusic.search.document.TrackDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface TrackSearchRepository extends ElasticsearchRepository<TrackDocument, String> {
    List<TrackDocument> findByTitleContaining(String query);
}
