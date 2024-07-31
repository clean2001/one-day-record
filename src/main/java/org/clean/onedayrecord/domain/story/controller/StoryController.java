package org.clean.onedayrecord.domain.story.controller;

import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.domain.story.dto.CreateStoryRequest;
import org.clean.onedayrecord.domain.story.dto.CreateStoryResponse;
import org.clean.onedayrecord.domain.story.dto.StoryResponse;
import org.clean.onedayrecord.domain.story.service.StoryService;
import org.clean.onedayrecord.global.response.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> getStoryList(@PageableDefault(size=10) Pageable pageable) {
        Page<StoryResponse> storyList = storyService.getStoryList(pageable);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("스토리 조회")
                .data(storyList)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<SuccessResponse> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);

        SuccessResponse response = SuccessResponse.builder()
                .code(HttpStatus.OK.value())
                .message("스토리 삭제")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
