package com.mangjakseon.repository;

import com.mangjakseon.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Member, String> {
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByWriterInfo(@Param("email") String email);
}
