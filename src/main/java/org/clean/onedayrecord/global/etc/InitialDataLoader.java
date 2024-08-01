package org.clean.onedayrecord.global.etc;

import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.member.entity.MemberRole;
import org.clean.onedayrecord.domain.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements CommandLineRunner {
    private final MemberRepository memberRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello world!");

        Member admin = Member.builder()
                .nickname("admin")
                .role(MemberRole.ADMIN)
                .socialId("1")
                .build();

        memberRepository.save(admin);
    }
}