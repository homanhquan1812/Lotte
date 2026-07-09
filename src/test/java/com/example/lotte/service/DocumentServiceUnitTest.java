package com.example.lotte.service;

import com.example.lotte.dto.document.request.CreateDocumentRequestDto;
import com.example.lotte.dto.document.response.DocumentResponseDto;
import com.example.lotte.entity.Document;
import com.example.lotte.enums.document.Status;
import com.example.lotte.exception.BadRequestException;
import com.example.lotte.mapper.DocumentMapper;
import com.example.lotte.repository.DocumentRepository;
import com.example.lotte.security.userDetails.CustomUserDetails;
import com.example.lotte.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceUnitTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Mock
    private CustomUserDetails currentUser;

    @Test
    @DisplayName("Create document successfully when code does not exist")
    void create_shouldReturnDto_whenCodeNotExists() {
        // Arrange
        CreateDocumentRequestDto requestDto = new CreateDocumentRequestDto(
                "DOC-001",
                "Title A",
                "Description A",
                "CAT1",
                Status.DRAFT
        );

        Document mappedEntity = new Document();
        Document savedEntity = new Document();
        ReflectionTestUtils.setField(savedEntity, "id", 1L);
        ReflectionTestUtils.setField(savedEntity, "status", Status.DRAFT);
        ReflectionTestUtils.setField(savedEntity, "createdBy", 100L);

        LocalDateTime now = LocalDateTime.now();

        DocumentResponseDto expectedDto = new DocumentResponseDto(
                1L,
                "DOC-001",
                "Title A",
                "Description A",
                "CAT1",
                Status.DRAFT,
                now,
                100L,
                "file.pdf"
        );

        given(documentRepository.existsByCode("DOC-001")).willReturn(false);
        given(documentMapper.toEntity(requestDto)).willReturn(mappedEntity);
        given(currentUser.getId()).willReturn(100L);
        given(documentRepository.save(mappedEntity)).willReturn(savedEntity);
        given(documentMapper.toDto(savedEntity)).willReturn(expectedDto);

        // Act
        DocumentResponseDto result = documentService.create(requestDto, currentUser);

        // Assert
        assertThat(result).isEqualTo(expectedDto);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.status()).isEqualTo(Status.DRAFT);
        assertThat(result.createdBy()).isEqualTo(100L);
        assertThat(mappedEntity.getStatus()).isEqualTo(Status.DRAFT);
        assertThat(mappedEntity.getCreatedBy()).isEqualTo(100L);

        verify(documentRepository).existsByCode("DOC-001");
        verify(documentRepository).save(mappedEntity);
        verify(documentMapper).toDto(savedEntity);
    }

    @Test
    @DisplayName("Throw BadRequestException when document code already exists")
    void create_shouldThrowBadRequestException_whenCodeAlreadyExists() {
        // Arrange
        CreateDocumentRequestDto requestDto = new CreateDocumentRequestDto(
                "DOC-001",
                "Title A",
                "Description A",
                "CAT1",
                Status.DRAFT
        );

        given(documentRepository.existsByCode("DOC-001")).willReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> documentService.create(requestDto, currentUser))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Document code already exists: DOC-001");

        verify(documentRepository).existsByCode("DOC-001");
        verify(documentRepository, never()).save(any());
        verifyNoInteractions(documentMapper);
    }
}
