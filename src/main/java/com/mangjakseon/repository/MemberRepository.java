package com.mangjakseon.repository;

import com.mangjakseon.entity.Member;
import com.querydsl.core.QueryResults;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Member m WHERE m.fromSocial = :social AND m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email, @Param("social") boolean social);

    @Query("SELECT m.memberId FROM Member m WHERE m.email = :memberId")
    Optional<Member> findByMember(@Param("memberId") String memberId);

    @Query("SELECT m.memberId FROM Member m WHERE m.email = :email")
    String findByMemberId(@Param("email") String email);

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    @Query("SELECT count(*) FROM Member WHERE nickname = :nickname")
    int countByNickname(@Param("nickname") String nickname);

    @Query("SELECT m.password FROM Member m WHERE m.email = :email")
    String findByPassword(@Param("email") String email);
}
