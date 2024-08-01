package org.clean.onedayrecord.domain.story.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.clean.onedayrecord.domain.common.BaseEntity;
import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.story.dto.UpdateStoryRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Story extends BaseEntity implements Serializable { // Serializable 구현 (캐싱을 위해)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imageUrl;

    private YesNo blockedYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateStory(UpdateStoryRequest updateStoryRequest) {
        this.content = updateStoryRequest.getContent();
        this.imageUrl = updateStoryRequest.getImageUrl();
    }
}
