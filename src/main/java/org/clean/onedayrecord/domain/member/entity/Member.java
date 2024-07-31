package org.clean.onedayrecord.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.clean.onedayrecord.domain.common.BaseEntity;
import org.clean.onedayrecord.domain.common.YesNo;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String profileImageUrl;

    private SocialType socialType;

    @Column(unique = true)
    private String socialId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

}
