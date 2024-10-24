package com.akihito.spring_security.dtos;

import jakarta.validation.constraints.NotNull;

public record SignlnRecordDTO(@NotNull String username, @NotNull String password) {
}
