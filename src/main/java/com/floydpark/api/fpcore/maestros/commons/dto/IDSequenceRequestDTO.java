package com.floydpark.api.fpcore.maestros.commons.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class IDSequenceRequestDTO implements Serializable {

    @NotEmpty(message = "The prefix should not be empty.")
    @Pattern(regexp = "[a-zA-Z]+", message = "The prefix should have only letters")
    private String prefix;

    @NotEmpty(message = "The description should not be empty.")
    private String description;

    @NotEmpty(message = "The mask should not be empty")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "The mask should be an alphanumeric value")
    private String mask;
}
