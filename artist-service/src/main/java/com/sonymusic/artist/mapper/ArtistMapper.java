package com.sonymusic.artist.mapper;

import com.sonymusic.artist.dto.ArtistRequest;
import com.sonymusic.artist.dto.ArtistResponse;
import com.sonymusic.artist.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Artist Mapper
 * 
 * MapStruct mapper for converting between Artist entities and DTOs.
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ArtistMapper {

    ArtistResponse toResponse(Artist artist);

    Artist toEntity(ArtistRequest request);

    void updateEntityFromRequest(ArtistRequest request, @MappingTarget Artist artist);
}
