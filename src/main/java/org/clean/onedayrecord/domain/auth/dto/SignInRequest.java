package org.clean.onedayrecord.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clean.onedayrecord.domain.member.entity.SocialType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    private SocialType socialType;
}
