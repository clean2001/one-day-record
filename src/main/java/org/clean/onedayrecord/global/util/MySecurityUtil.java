package org.clean.onedayrecord.global.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MySecurityUtil {
    public Long getMemberIdFromSecurity() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
