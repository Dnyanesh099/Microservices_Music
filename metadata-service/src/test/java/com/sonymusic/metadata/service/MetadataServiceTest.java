package com.sonymusic.metadata.service;

import com.sonymusic.metadata.dto.TrackDTO;
import com.sonymusic.metadata.service.MetadataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MetadataServiceTest {

    @Mock
    private com.sonymusic.metadata.repository.TrackRepository trackRepository;

    @Mock
    private com.sonymusic.metadata.mapper.TrackMapper trackMapper;

    @Mock
    private org.springframework.kafka.core.KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private MetadataService metadataService;

    @Test
    public void testGetTrackByIsrc_NotFound() {
        when(trackRepository.findByIsrc("INVALID")).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            metadataService.getTrackByIsrc("INVALID");
        });
    }
}
