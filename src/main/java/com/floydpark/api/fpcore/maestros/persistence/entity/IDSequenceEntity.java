package com.floydpark.api.fpcore.maestros.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class IDSequenceEntity implements Serializable {

    @Id
    private String prefix;
    private LocalDate creationDate;
    private String description;
    private int sequence;
    private String mask;
}
