package org.clean.onedayrecord.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.story.entity.Story;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    @NotNull
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private String nickname;

    @NotNull
    private String content;

    @NotNull
    private String imageUrl;

    public static StoryResponse fromEntity(Story story, Member member) {
        return StoryResponse.builder()
                .id(story.getId())
                .content(story.getContent())
                .nickname(member.getNickname())
                .memberId(member.getId())
                .imageUrl(story.getImageUrl())
                .build();
    }
}
