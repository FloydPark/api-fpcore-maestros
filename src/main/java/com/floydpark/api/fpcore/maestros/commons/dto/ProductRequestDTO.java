package com.floydpark.api.fpcore.maestros.commons.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class ProductRequestDTO implements Serializable {

    @NotEmpty(message = "The id should not be empty.")
    private String id;

    @NotEmpty(message = "The name should not be empty.")
    private String name;

    @NotEmpty(message = "The value should not be empty.")
    @Pattern(regexp = "^[0-9]+$", message = "The value should be a number.")
    private String value;

    private boolean iva;

    @NotEmpty(message = "The product should have a photo.")
    private String urlPhoto;
}
