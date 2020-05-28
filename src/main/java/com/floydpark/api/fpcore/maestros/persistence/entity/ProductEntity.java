package com.floydpark.api.fpcore.maestros.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class ProductEntity implements Serializable {

    @Id
    private String id;
    private String name;
    private double value;
    private boolean iva;
    private String urlPhoto;

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", iva=" + iva +
                ", urlPhoto='" + urlPhoto + '\'' +
                '}';
    }
}
