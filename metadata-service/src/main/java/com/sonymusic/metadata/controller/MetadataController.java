package com.sonymusic.metadata.controller;

import com.sonymusic.metadata.dto.TrackDTO;
import com.sonymusic.metadata.service.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
@Slf4j
public class MetadataController {

    private final MetadataService metadataService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackDTO createTrack(@RequestBody TrackDTO trackDTO) {
        log.info("Creating track: {}", trackDTO);
        return metadataService.createTrack(trackDTO);
    }

    @GetMapping("/{isrc}")
    public TrackDTO getTrack(@PathVariable String isrc) {
        log.info("Getting track by ISRC: {}", isrc);
        return metadataService.getTrackByIsrc(isrc);
    }
}
