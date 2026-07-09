package com.example.lotte.controller;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.document.request.CreateDocumentRequestDto;
import com.example.lotte.dto.document.request.DocumentSearchRequestDto;
import com.example.lotte.dto.document.request.UpdateDocumentRequestDto;
import com.example.lotte.dto.document.response.DocumentResponseDto;
import com.example.lotte.dto.document.response.StatusCountResponseDto;
import com.example.lotte.enums.document.Status;
import com.example.lotte.enums.user.Role;
import com.example.lotte.security.userDetails.CustomUserDetails;
import com.example.lotte.service.DocumentService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.lotte.common.constants.DocumentSortConstants.*;

@Tag(name = "Document Management", description = "Document Management APIs")
@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Get documents with pagination")
    @GetMapping
    public PageResponse<DocumentResponseDto> getPage(
            @RequestParam(defaultValue = "0") @Min(MIN_PAGE) int page,
            @RequestParam(defaultValue = "10") @Min(MIN_SIZE) @Max(MAX_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sort,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION)
            @Pattern(regexp = "^(asc|desc)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Direction must be asc|desc|ASC|DESC")
            String direction,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        String sortField = ALLOWED_SORT_FIELDS.contains(sort) ? sort : DEFAULT_SORT_FIELD;
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Long createdByFilter = currentUser.getRole() == Role.STAFF ? currentUser.getId() : null;

        return documentService.getPage(
                new DocumentSearchRequestDto(code, title, status, category, createdByFilter),
                pageable
        );
    }

    @Operation(summary = "Get a document by id")
    @GetMapping("/{id}")
    public DocumentResponseDto getById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        return documentService.getById(id, currentUser);
    }

    @Operation(summary = "Create a document")
    @PostMapping
    public DocumentResponseDto create(
            @Valid @RequestBody CreateDocumentRequestDto request,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        return documentService.create(request, currentUser);
    }

    @Operation(summary = "Update a document")
    @PatchMapping("/{id}")
    public DocumentResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDocumentRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        return documentService.update(id, requestDto, currentUser);
    }

    @Operation(summary = "Delete a document (ADMIN only)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Count documents grouped by status")
    @GetMapping("/reports/status-count")
    public List<StatusCountResponseDto> countByStatus() {
        return documentService.countByStatus();
    }
}
