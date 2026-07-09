package com.example.lotte.service.impl;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.document.request.CreateDocumentRequestDto;
import com.example.lotte.dto.document.request.DocumentSearchRequestDto;
import com.example.lotte.dto.document.request.UpdateDocumentRequestDto;
import com.example.lotte.dto.document.response.DocumentResponseDto;
import com.example.lotte.dto.document.response.StatusCountResponseDto;
import com.example.lotte.entity.Document;
import com.example.lotte.enums.auditLog.Action;
import com.example.lotte.enums.auditLog.AuditStatus;
import com.example.lotte.enums.document.Status;
import com.example.lotte.enums.user.Role;
import com.example.lotte.exception.BadRequestException;
import com.example.lotte.exception.ResourceNotFoundException;
import com.example.lotte.mapper.DocumentMapper;
import com.example.lotte.repository.DocumentRepository;
import com.example.lotte.security.userDetails.CustomUserDetails;
import com.example.lotte.service.AuditLogService;
import com.example.lotte.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final AuditLogService auditLogService;
    private final DocumentMapper documentMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DocumentResponseDto> getPage(DocumentSearchRequestDto filter, Pageable pageable) {
        return PageResponse.from(
                documentRepository.findDocumentPage(
                        toLikePattern(filter.code()),
                        toLikePattern(filter.title()),
                        filter.status(),
                        filter.category(),
                        filter.createdBy(),
                        pageable
                ).map(documentMapper::projectionToDto)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDto getById(Long id, CustomUserDetails currentUser) {
        Document document = findDocumentById(id);
        assertOwnership(document, currentUser);
        return documentMapper.toDto(document);
    }

    @Override
    @Transactional
    public DocumentResponseDto create(CreateDocumentRequestDto requestDto, CustomUserDetails currentUser) {
        if (documentRepository.existsByCode(requestDto.code())) {
            throw new BadRequestException("Document code already exists: " + requestDto.code());
        }

        Document newDocument = documentMapper.toEntity(requestDto);

        newDocument.setStatus(Status.DRAFT);
        newDocument.setCreatedBy(currentUser.getId());

        Document saved = documentRepository.save(newDocument);

        return documentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public DocumentResponseDto update(Long id, UpdateDocumentRequestDto requestDto, CustomUserDetails currentUser) {
        try {
            Document existingDocument = findDocumentById(id);
            assertOwnership(existingDocument, currentUser);

            documentMapper.updateEntityFromDto(requestDto, existingDocument);

            if (requestDto.status() != null) {
                existingDocument.setStatus(requestDto.status());
            }

            Document saved = documentRepository.save(existingDocument);
            auditLogService.log(Action.UPDATE_DOCUMENT, saved.getId(), AuditStatus.SUCCESS, "Document updated successfully");
            return documentMapper.toDto(saved);
        } catch (Exception e) {
            auditLogService.log(Action.UPDATE_DOCUMENT, id, AuditStatus.FAILURE, "Failed to update document");
            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        documentRepository.delete(findDocumentById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusCountResponseDto> countByStatus() {
        return documentRepository.countByStatus().stream()
                .map(p -> new StatusCountResponseDto(p.getStatus(), p.getCount()))
                .toList();
    }

    // Private helpers
    private Document findDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found: " + id));
    }

    private void assertOwnership(Document doc, CustomUserDetails currentUser) {
        boolean isStaff = currentUser.getRole() == Role.STAFF;
        boolean isOwner = doc.getCreatedBy().equals(currentUser.getId());

        if (isStaff && !isOwner) {
            throw new BadRequestException("You can only access documents you created");
        }
    }

    private String toLikePattern(String value) {
        return (value == null || value.isBlank())
                ? null
                : "%" + value.toLowerCase() + "%";
    }
}
