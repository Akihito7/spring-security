package com.akihito.spring_security.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductsRecordDTO(@NotBlank String name, @NotNull BigDecimal price) {}
