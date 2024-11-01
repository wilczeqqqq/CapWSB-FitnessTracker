package com.capgemini.wsb.fitnesstracker.user.internal;

import jakarta.annotation.Nullable;

public record UserFindByEmailDto(
        @Nullable
        Long id,
        String email) {
}
