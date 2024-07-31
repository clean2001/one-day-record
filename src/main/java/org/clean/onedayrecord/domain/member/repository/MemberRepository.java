package org.clean.onedayrecord.domain.member.repository;

import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialIdAndDelYn(String socialId, YesNo delYn);

    Optional<Member> findByIdAndDelYn(Long id, YesNo delYn);

}
