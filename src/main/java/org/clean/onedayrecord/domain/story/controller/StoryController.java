package org.clean.onedayrecord.domain.story.controller;

import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.domain.story.dto.CreateStoryRequest;
import org.clean.onedayrecord.domain.story.dto.CreateStoryResponse;
import org.clean.onedayrecord.domain.story.dto.StoryResponse;
import org.clean.onedayrecord.domain.story.dto.UpdateStoryRequest;
import org.clean.onedayrecord.domain.story.service.StoryService;
import org.clean.onedayrecord.global.response.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/story")
@RestController
public class StoryController {
    private final StoryService storyService;
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createStory(@Validated @RequestBody CreateStoryRequest createStoryRequest) {
        CreateStoryResponse createStoryResponse = storyService.createStory(createStoryRequest);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("스토리 생성")
                .data(createStoryResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 캐싱
    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getStoryList(@PageableDefault(size=10) Pageable pageable) {
        Page<StoryResponse> storyList = storyService.getStoryListCache(pageable);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("스토리 조회")
                .data(storyList)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 캐싱
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getStory(@PathVariable Long id) {
        StoryResponse story = storyService.getStoryCache(id);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("스토리 조회")
                .data(story)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 캐싱
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SuccessResponse> deleteStory(@PathVariable Long id) {
        storyService.deleteStoryCache(id);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("스토리 삭제")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 캐싱 - 업데이트
    @PatchMapping("/{id}/update")
    public ResponseEntity<SuccessResponse> updateStory(@PathVariable Long id,
                                                       @RequestBody UpdateStoryRequest updateStoryRequest) {
        storyService.updateStory(id, updateStoryRequest);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("업데이트 성공")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}/rdb")
    public ResponseEntity<SuccessResponse> getStoryRdb(@PathVariable Long id) {
        StoryResponse story = storyService.getStory(id);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("스토리 조회")
                .data(story)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //== 관리자는 특정 글을 삭제할 수 있음 ==//
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete-by-admin")
    public ResponseEntity<SuccessResponse> deleteStoryForAdmin(@PathVariable Long id) {
        storyService.deleteStoryForAdminCache(id);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("[관리자] 성공적으로 스토리를 삭제했습니다.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
