package org.clean.onedayrecord.domain.story.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.story.entity.Story;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStoryRequest {
    @NotNull
    private String content;
    @NotNull
    private String imageUrl;

    public static Story toEntity(CreateStoryRequest createStoryRequest, Member member) {
        return Story.builder()
                .content(createStoryRequest.getContent())
                .imageUrl(createStoryRequest.getImageUrl())
                .blockedYn(YesNo.N)
                .member(member)
                .build();
    }
}
