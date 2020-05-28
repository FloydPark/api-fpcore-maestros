package com.floydpark.api.fpcore.maestros.api;

import com.floydpark.api.fpcore.maestros.business.ProductBusiness;
import com.floydpark.api.fpcore.maestros.persistence.entity.ProductEntity;
import com.floydpark.lib.commons.dto.ErrorDTO;
import com.floydpark.lib.commons.dto.ResponseDTO;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@AllArgsConstructor
public class ProductAPI {

    private ProductBusiness business;

    @GetMapping("/")
    public ResponseEntity getProducts() {

        ResponseEntity responseEntity = null;

        try {

            business.createFooProduct();
            List<ProductEntity> products = business.getProducts();
            ResponseDTO<List<ProductEntity>> responseDTO = new ResponseDTO<>();
            responseDTO.setData(products);
            responseEntity = ResponseEntity.ok(responseDTO);

        } catch (ElementNotFoundException e){

            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(e.getErrors());
            responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id") String id) {

        ResponseEntity responseEntity;

        try {

            ProductEntity productEntity = business.getProductById(id);
            ResponseDTO<ProductEntity> responseDTO = new ResponseDTO<>();
            responseDTO.setData(productEntity);
            responseEntity = ResponseEntity.ok(responseDTO);

        } catch (ElementNotFoundException e){

            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(e.getErrors());
            responseEntity = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}
