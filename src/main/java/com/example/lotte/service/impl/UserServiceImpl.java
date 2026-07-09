package com.example.lotte.service.impl;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.user.request.CreateUserRequestDto;
import com.example.lotte.dto.user.request.UpdateUserRequestDto;
import com.example.lotte.dto.user.response.UserResponseDto;
import com.example.lotte.entity.User;
import com.example.lotte.exception.ResourceNotFoundException;
import com.example.lotte.mapper.UserMapper;
import com.example.lotte.projection.UserProjection;
import com.example.lotte.repository.UserRepository;
import com.example.lotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponseDto> getPage(Pageable pageable) {
        return PageResponse.from(userRepository
                .findAllUserProjections(pageable)
                .map(userMapper::projectionToDto)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getById(Long userId) {
        return userMapper.projectionToDto(findUserProjectionById(userId));
    }

    @Override
    @Transactional
    public UserResponseDto create(CreateUserRequestDto requestDto) {
        User user = userMapper.toEntity(requestDto);
        user.setRole(requestDto.role());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto update(Long userId, UpdateUserRequestDto requestDto) {
        User existingUser = findUserById(userId);

        userMapper.updateEntityFromDto(requestDto, existingUser);

        User updatedUser = userRepository.save(existingUser);

        log.info("User {} updated successfully by {}.",
                updatedUser.getId(),
                userId
        );

        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userRepository.delete(findUserById(userId));
        log.info("User deleted: id={}", userId);
    }

    // Private helpers
    private UserProjection findUserProjectionById(Long userId) {
        return userRepository.findUserProjectionById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
