package com.caritas.backend.common;

import com.caritas.backend.common.errors.BadRequestException;

public class ValidateHeaderUserId {
    public static void validateOrThrow(String headerUserId, String userId) {
        if (headerUserId != null && !headerUserId.equals(userId)) {
            throw new BadRequestException("Authenticated user does not match with the sent user");
        }
    }
}
