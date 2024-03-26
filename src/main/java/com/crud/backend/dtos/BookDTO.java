package com.crud.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Long id;
    @NotEmpty(message = "O título deve ser informado.")
    private String title;
    @NotEmpty(message = "O autor deve ser informado.")
    private String author;
    private String format;
    @Positive(message = "Quantidade deve ser um valor maior que zero.")
    private int amount;
}
