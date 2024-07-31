package org.clean.onedayrecord.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clean.onedayrecord.domain.auth.dto.*;
import org.clean.onedayrecord.domain.auth.jwt.JwtProvider;
import org.clean.onedayrecord.domain.common.YesNo;
import org.clean.onedayrecord.domain.member.entity.Member;
import org.clean.onedayrecord.domain.member.entity.MemberRole;
import org.clean.onedayrecord.domain.member.entity.SocialType;
import org.clean.onedayrecord.domain.member.repository.MemberRepository;
import org.clean.onedayrecord.global.config.GoogleOAuthConfig;
import org.clean.onedayrecord.global.exception.BaseException;
import org.clean.onedayrecord.global.exception.exceptionType.AuthExceptionType;
import org.clean.onedayrecord.global.util.MySecurityUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.clean.onedayrecord.domain.member.entity.SocialType.*;
import static org.clean.onedayrecord.global.exception.exceptionType.AuthExceptionType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final GoogleOAuthConfig googleOAuthConfig;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final MySecurityUtil mySecurityUtil;
    public GoogleResponse signIn(String socialAccessToken, SocialType socialType) {
        if(socialType.equals(GOOGLE)) {
            return getMemberInfoFromGoogle(socialAccessToken);
        } else {
            throw new BaseException(INVALID_SOCIAL_TYPE);
        }
    }
    public String getTokenFromGoogle(String code) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        GoogleTokenRequest googleRequest = GoogleTokenRequest.builder()
                        .client_secret(googleOAuthConfig.getGoogleClientSecret())
                        .client_id(googleOAuthConfig.getGoogleClientId())
                        .code(code)
                        .grant_type("authorization_code")
                        .redirect_uri("http://localhost:8080/api/auth/oauth/google").build();

        ResponseEntity<GoogleTokenResponse> result = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleRequest, GoogleTokenResponse.class);
        String socialAccessToken = result.getBody().getAccess_token();

        return socialAccessToken;
    }

    public GoogleResponse getMemberInfoFromGoogle(String socialAccessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + socialAccessToken);

        final HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange("https://www.googleapis.com/userinfo/v2/me",
                        HttpMethod.GET,
                        httpEntity,
                        GoogleResponse.class)
                        .getBody();
    }


    @Transactional
    public SignInResponse signIn(String socialAccessToken) {
        GoogleResponse memberInfo = getMemberInfoFromGoogle(socialAccessToken);
        Optional<Member> memberOpt = memberRepository.findBySocialIdAndDelYn(memberInfo.getId(), YesNo.N);

        Member member;
        if(memberOpt.isEmpty()) {
            member = Member.builder()
                    .role(MemberRole.USER)
                    .socialId(memberInfo.getId())
                    .nickname(memberInfo.getName())
                    .build();
            memberRepository.save(member);
        } else {
            member = memberOpt.get();
        }

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole());
//        String refreshToken = jwtProvider.createRefreshToken(member.getId(), member.getRole());
        return SignInResponse.builder()
                .accessToken(accessToken)
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

    }

    @Transactional
    public void withdraw() {
        Long memberId = mySecurityUtil.getMemberIdFromSecurity();
        Optional<Member> memberOpt = memberRepository.findByIdAndDelYn(memberId, YesNo.N);

        Member member = memberOpt.orElseThrow(() -> new BaseException(NO_VALID_MEMBER));
        member.updateDelYn(YesNo.Y);
    }
}
