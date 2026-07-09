package com.example.lotte.mapper;

import com.example.lotte.dto.user.request.CreateUserRequestDto;
import com.example.lotte.dto.user.request.UpdateUserRequestDto;
import com.example.lotte.dto.user.response.UserResponseDto;
import com.example.lotte.entity.User;
import com.example.lotte.enums.user.Role;
import com.example.lotte.projection.UserProjection;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(CreateUserRequestDto requestDto);

    UserResponseDto toDto(User user);

    UserResponseDto projectionToDto(UserProjection userProjection);

    @Mapping(target = "role", ignore = true)
    void updateEntityFromDto(UpdateUserRequestDto requestDto, @MappingTarget User user);
}
