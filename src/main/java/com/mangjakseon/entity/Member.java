package com.mangjakseon.entity;

import com.mangjakseon.utils.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "com.mangjakseon.entity.MemberIdGenerator") //컬럼 데이터 생성 공식의 이름과 경로
    @Column(name = "member_id", length = 60)
    private String memberId;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(unique = true, length = 50, nullable = false)
    private String nickname;

    private boolean fromSocial;

    @Column(length = 100)
    private String profileImage;

    @Column
    private boolean movieViewed;

    @Column(length = 100)
    private String movieId;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void modifyPassword(String password){
        this.password = password;
    }

    public void modifyNickname(String nickname){
        this.nickname = nickname;
    }

    public void addMemberRole(MemberRole memberRole) { roleSet.add(memberRole); }

}
