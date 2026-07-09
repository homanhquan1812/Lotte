package com.example.lotte.repository;

import com.example.lotte.entity.User;
import com.example.lotte.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT
            u.id,
            u.username,
            u.role
        FROM User u
    """)
    Page<UserProjection> findAllUserProjections(Pageable pageable);

    @Query("""
        SELECT
            u.id,
            u.username,
            u.role
        FROM User u
        WHERE u.id = :id
    """)
    Optional<UserProjection> findUserProjectionById(@Param("id") Long id);

    @Query("""
    SELECT u.id as id,
           u.username as username,
           u.password as password,
           u.role as role
    FROM User u
    WHERE u.username = :username
    """)
    Optional<UserProjection> findUserProjectionByUsername(String username);
}
