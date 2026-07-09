package com.example.lotte.mapper;

import com.example.lotte.dto.document.request.CreateDocumentRequestDto;
import com.example.lotte.dto.document.request.UpdateDocumentRequestDto;
import com.example.lotte.dto.document.response.DocumentResponseDto;
import com.example.lotte.dto.user.request.UpdateUserRequestDto;
import com.example.lotte.entity.Document;
import com.example.lotte.entity.User;
import com.example.lotte.projection.DocumentProjection;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DocumentMapper {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    Document toEntity(CreateDocumentRequestDto requestDto);

    DocumentResponseDto toDto(Document document);

    DocumentResponseDto projectionToDto(DocumentProjection documentProjection);

    void updateEntityFromDto(UpdateDocumentRequestDto requestDto, @MappingTarget Document document);
}
