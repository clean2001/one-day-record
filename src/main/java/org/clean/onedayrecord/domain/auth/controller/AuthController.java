package org.clean.onedayrecord.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.clean.onedayrecord.domain.auth.dto.GoogleResponse;
import org.clean.onedayrecord.domain.auth.dto.SignInRequest;
import org.clean.onedayrecord.domain.auth.dto.SignInResponse;
import org.clean.onedayrecord.domain.auth.service.AuthService;
import org.clean.onedayrecord.global.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController{
    private static final String SOCIAL_TOKEN_NAME = "X-AUTH-TOKEN";

    private final AuthService authService;


    //== google OAuth2 test용 API 액세스 토큰 발급 ==//
    @GetMapping("/oauth/google")
    public String googleTest(@RequestParam("code") String code) {
        return authService.getTokenFromGoogle(code);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse> signIn(@RequestHeader("X-AUTH-TOKEN") final String socialAccessToken) {
        SignInResponse signInResponse = authService.signIn(socialAccessToken);
        SuccessResponse response = SuccessResponse.builder()
                .data(signInResponse)
                .message("로그인 되셨습니다.")
                .code(HttpStatus.CREATED.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<SuccessResponse> withdraw() {
        authService.withdraw();
        SuccessResponse response = SuccessResponse.builder()
                .message("탈퇴되었습니다.")
                .code(HttpStatus.OK.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
