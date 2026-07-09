package com.example.lotte.service;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.document.request.CreateDocumentRequestDto;
import com.example.lotte.dto.document.request.DocumentSearchRequestDto;
import com.example.lotte.dto.document.request.UpdateDocumentRequestDto;
import com.example.lotte.dto.document.response.DocumentResponseDto;
import com.example.lotte.dto.document.response.StatusCountResponseDto;
import com.example.lotte.security.userDetails.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentService {

    PageResponse<DocumentResponseDto> getPage(DocumentSearchRequestDto filter, Pageable pageable);
    DocumentResponseDto getById(Long id, CustomUserDetails currentUser);
    DocumentResponseDto create(CreateDocumentRequestDto request, CustomUserDetails currentUser);
    DocumentResponseDto update(Long id, UpdateDocumentRequestDto requestDto, CustomUserDetails currentUser);
    void delete(Long id);
    List<StatusCountResponseDto> countByStatus();
}
