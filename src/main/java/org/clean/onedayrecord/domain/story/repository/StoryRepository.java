package org.clean.onedayrecord.domain.story.repository;

import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.story.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {
    Optional<Story> findByIdAndDelYn(Long id, YesNo delYn);
    Page<Story> findAllByDelYnOrderByCreatedTimeDesc(Pageable pageable, YesNo delYn);
}
