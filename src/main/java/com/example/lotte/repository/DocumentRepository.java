package com.example.lotte.repository;

import com.example.lotte.entity.Document;
import com.example.lotte.enums.document.Status;
import com.example.lotte.projection.DocumentProjection;
import com.example.lotte.projection.StatusCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    boolean existsByCode(String code);

    @Query(value = """
            SELECT
                d.id           AS id,
                d.code         AS code,
                d.title        AS title,
                d.description  AS description,
                d.category     AS category,
                d.status       AS status,
                d.createdAt    AS createdAt,
                d.createdBy    AS createdBy,
                d.fileName     AS fileName
            FROM Document d
            WHERE (:code      IS NULL OR LOWER(d.code) LIKE :code)
              AND (:title     IS NULL OR LOWER(d.title) LIKE :title)
              AND (:status    IS NULL OR d.status = :status)
              AND (:category  IS NULL OR d.category = :category)
              AND (:createdBy IS NULL OR d.createdBy = :createdBy)
            """,
            countQuery = """
            SELECT COUNT(d.id)
            FROM Document d
            WHERE (:code      IS NULL OR LOWER(d.code) LIKE :code)
              AND (:title     IS NULL OR LOWER(d.title) LIKE :title)
              AND (:status    IS NULL OR d.status = :status)
              AND (:category  IS NULL OR d.category = :category)
              AND (:createdBy IS NULL OR d.createdBy = :createdBy)
            """
    )
    Page<DocumentProjection> findDocumentPage(
            @Param("code") String code,
            @Param("title") String title,
            @Param("status") Status status,
            @Param("category") String category,
            @Param("createdBy") Long createdBy,
            Pageable pageable
    );

    @Query("""
        SELECT d.status AS status, COUNT(d) AS count
        FROM Document d
        GROUP BY d.status
        """)
    List<StatusCountProjection> countByStatus();
}
