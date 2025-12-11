package com.sonymusic.metadata.mapper;

import com.sonymusic.metadata.domain.Track;
import com.sonymusic.metadata.dto.TrackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackDTO toDTO(Track track);
    Track toEntity(TrackDTO trackDTO);
}
