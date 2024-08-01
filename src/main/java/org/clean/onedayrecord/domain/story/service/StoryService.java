package org.clean.onedayrecord.domain.story.service;

import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.member.repository.MemberRepository;
import org.clean.onedayrecord.domain.story.dto.CreateStoryRequest;
import org.clean.onedayrecord.domain.story.dto.CreateStoryResponse;
import org.clean.onedayrecord.domain.story.dto.StoryResponse;
import org.clean.onedayrecord.domain.story.dto.UpdateStoryRequest;
import org.clean.onedayrecord.domain.story.entity.Story;
import org.clean.onedayrecord.domain.story.repository.StoryRepository;
import org.clean.onedayrecord.global.exception.BaseException;
import org.clean.onedayrecord.global.exception.exceptionType.StoryExceptionType;
import org.clean.onedayrecord.global.util.MySecurityUtil;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//import javax.persistence.Cacheable;

import static org.clean.onedayrecord.global.exception.exceptionType.MemberExceptionType.NO_VALID_MEMBER;
import static org.clean.onedayrecord.global.exception.exceptionType.StoryExceptionType.*;

@RequiredArgsConstructor
@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final MemberRepository memberRepository;
    private final MySecurityUtil mySecurityUtil;
    private final CacheManager cacheManager; // 의존성 주입을 받아야하나..?
    public CreateStoryResponse createStory(CreateStoryRequest createStoryRequest) {
        Member member = memberRepository
                .findByIdAndDelYn(mySecurityUtil.getMemberIdFromSecurity(), YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_MEMBER));
        Story story = CreateStoryRequest.toEntity(createStoryRequest, member);
        Story savedStory = storyRepository.save(story);

        return new CreateStoryResponse(savedStory.getId());
    }

    public Page<StoryResponse> getStoryList(Pageable pageable) {
        Page<Story> storyList = storyRepository.findAllByDelYnOrderByCreatedTimeDesc(pageable, YesNo.N);

        return storyList.map(story -> {
            Member member = memberRepository.findByIdAndDelYn(story.getMember().getId(), YesNo.N)
                    .orElseThrow(() -> new BaseException(NO_VALID_MEMBER));

            return StoryResponse.fromEntity(story, member);
        });
    }

    @Transactional
    public void deleteStory(Long id) {
        Member member = memberRepository
                .findByIdAndDelYn(mySecurityUtil.getMemberIdFromSecurity(), YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_MEMBER));

        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        if(!story.getMember().equals(member)) {
            throw new BaseException(CANNOT_DELETE);
        }

        story.updateDelYn(YesNo.Y);
    }

    @Transactional
    public void deleteStoryForAdmin(Long id) {
        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        story.updateDelYn(YesNo.Y);
    }

    @Transactional
    public StoryResponse getStory(Long id) {
        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        return StoryResponse.fromEntity(story, story.getMember());
    }



    //==== 캐싱 전용 메서드 ====//

    @Cacheable(value = "storyCache", key = "#id", cacheManager = "cacheManager")
    @Transactional
    public StoryResponse getStoryCache(Long id) {
        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        return StoryResponse.fromEntity(story, story.getMember());
    }

//    @Cacheable(value = "storyCache", key = "#result.id")
    public Page<StoryResponse> getStoryListCache(Pageable pageable) {
        Page<Story> storyList = storyRepository.findAllByDelYnOrderByCreatedTimeDesc(pageable, YesNo.N);

        return storyList.map(story -> {
            Member member = memberRepository.findByIdAndDelYn(story.getMember().getId(), YesNo.N)
                    .orElseThrow(() -> new BaseException(NO_VALID_MEMBER));

            return StoryResponse.fromEntity(story, member);
        });
    }

    @CachePut(value = "storyCache", key = "#result.id", cacheManager = "cacheManager")
    @Transactional
    public StoryResponse updateStory(Long storyId, UpdateStoryRequest updateStoryRequest) {
        // 검증

        Story story = storyRepository.findByIdAndDelYn(storyId, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        story.updateStory(updateStoryRequest);

        return StoryResponse.fromEntity(story, story.getMember()); // 이걸 써줘야하는 것 같은데
    }

    @CacheEvict(value = "storyCache", key = "#id", cacheManager = "cacheManager")
    @Transactional
    public void deleteStoryCache(Long id) {
        Member member = memberRepository
                .findByIdAndDelYn(mySecurityUtil.getMemberIdFromSecurity(), YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_MEMBER));

        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        if(!story.getMember().equals(member)) {
            throw new BaseException(CANNOT_DELETE);
        }

        story.updateDelYn(YesNo.Y);
    }

    @CacheEvict(value = "storyCache", key = "#id", cacheManager = "cacheManager")
    @Transactional
    public void deleteStoryForAdminCache(Long id) {
        Story story = storyRepository.findByIdAndDelYn(id, YesNo.N)
                .orElseThrow(() -> new BaseException(NO_VALID_STORY));

        story.updateDelYn(YesNo.Y);
    }

    @Cacheable(value = "storyCache", key = "#id", cacheManager = "cacheManager")
    public Story findStory(Long id) {
        return storyRepository.findByIdAndDelYn(id, YesNo.N).orElseThrow(() -> new BaseException(NO_VALID_STORY));
    }

}
