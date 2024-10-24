package com.akihito.spring_security.dtos;

import jakarta.validation.constraints.NotNull;

public record RegisterRecordDTO
        (@NotNull String username, @NotNull String password, @NotNull String roles) {
}
