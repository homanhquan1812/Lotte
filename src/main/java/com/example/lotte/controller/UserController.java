package com.example.lotte.controller;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.user.request.CreateUserRequestDto;
import com.example.lotte.dto.user.request.UpdateUserRequestDto;
import com.example.lotte.dto.user.response.UserResponseDto;
import com.example.lotte.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.lotte.common.constants.UserSortConstants.*;

@Tag(name = "User Management", description = "User Management APIs")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    // [GET] /api/v1/user?...=...&...=...
    @Operation(summary = "Get users with pagination")
    @GetMapping
    public PageResponse<UserResponseDto> getPage(
            @RequestParam(defaultValue = "0") @Min(MIN_PAGE) int page,
            @RequestParam(defaultValue = "10") @Min(MIN_SIZE) @Max(MAX_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sort,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION)
            @Pattern(regexp = "^(asc|desc)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Direction must be asc|desc|ASC|DESC")
            String direction) {
        /* Map (Mostly used with nativeQuery in Repository):
        String sortField = ALLOWED_SORT_FIELDS.getOrDefault(sort, "createdAt");
         */
        // Set:
        String sortField = ALLOWED_SORT_FIELDS.contains(sort)
                ? sort
                : DEFAULT_SORT_FIELD;

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        return userService.getPage(pageable);
    }

    // [GET] /api/v1/user/{userId}
    @Operation(summary = "Get user by ID")
    @GetMapping("/{userId}")
    public UserResponseDto getById(@PathVariable @Min(1) Long userId) {
        return userService.getById(userId);
    }

    // [POST] /api/v1/user
    @PostMapping
    @Operation(summary = "Create new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(
            @Valid @RequestBody CreateUserRequestDto requestDto
    ) {
        return userService.create(requestDto);
    }

    // [PATCH] /api/v1/user/{userId}
    @Operation(summary = "Update user by ID")
    @PatchMapping("/{userId}")
    public UserResponseDto update(
            @PathVariable @Min(1) Long userId,
            @Valid @RequestBody UpdateUserRequestDto requestDto) {
        return userService.update(userId, requestDto);
    }

    // [DELETE] /api/v1/user/{userId}
    @Operation(summary = "Delete user permanently by ID")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable @Min(1) Long userId
    ) {
        userService.delete(userId);
    }
}
