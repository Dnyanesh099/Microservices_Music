package com.sonymusic.metadata.mapper;

import com.sonymusic.metadata.domain.Track;
import com.sonymusic.metadata.dto.TrackDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-11T16:46:14+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class TrackMapperImpl implements TrackMapper {

    @Override
    public TrackDTO toDTO(Track track) {
        if ( track == null ) {
            return null;
        }

        TrackDTO trackDTO = new TrackDTO();

        trackDTO.setId( track.getId() );
        trackDTO.setTitle( track.getTitle() );
        trackDTO.setIsrc( track.getIsrc() );
        trackDTO.setDurationSec( track.getDurationSec() );
        trackDTO.setAlbumId( track.getAlbumId() );

        return trackDTO;
    }

    @Override
    public Track toEntity(TrackDTO trackDTO) {
        if ( trackDTO == null ) {
            return null;
        }

        Track track = new Track();

        track.setId( trackDTO.getId() );
        track.setTitle( trackDTO.getTitle() );
        track.setIsrc( trackDTO.getIsrc() );
        track.setDurationSec( trackDTO.getDurationSec() );
        track.setAlbumId( trackDTO.getAlbumId() );

        return track;
    }
}
