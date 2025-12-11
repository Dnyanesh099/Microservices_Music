package com.sonymusic.search.controller;

import com.sonymusic.search.document.TrackDocument;
import com.sonymusic.search.repository.TrackSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final TrackSearchRepository repository;

    @GetMapping("/tracks")
    public List<TrackDocument> searchTracks(@RequestParam String query) {
        return repository.findByTitleContaining(query);
    }
}
