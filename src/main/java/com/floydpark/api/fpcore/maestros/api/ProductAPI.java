package com.floydpark.api.fpcore.maestros.api;

import com.floydpark.api.fpcore.maestros.business.ProductBusiness;
import com.floydpark.api.fpcore.maestros.commons.dto.ProductRequestDTO;
import com.floydpark.api.fpcore.maestros.persistence.entity.ProductEntity;
import com.floydpark.lib.commons.dto.ResponseDTO;
import com.floydpark.lib.commons.exception.BusinessException;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import com.floydpark.lib.commons.exception.ValidationException;
import com.floydpark.lib.commons.util.ResponseEntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/v1/product")
@AllArgsConstructor
public class ProductAPI {

    private ProductBusiness business;

    @GetMapping("/")
    public ResponseEntity<Serializable> getProducts() {

        ResponseEntity<Serializable> responseEntity;

        try {

            List<ProductEntity> products = business.getProducts();
            responseEntity = ResponseEntityUtil.INSTANCE.handleCollectionResponse(products);

        } catch (ElementNotFoundException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }

    @PostMapping("/")
    public ResponseEntity<Serializable> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {

        ResponseEntity<Serializable> responseEntity;

        try {

            ProductEntity productEntity = business.addProduct(productRequestDTO);
            responseEntity = ResponseEntityUtil.INSTANCE.handleSingleResponse(productEntity, HttpStatus.CREATED);

        } catch (ValidationException | BusinessException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serializable> getProductById(@PathVariable("id") String id) {

        ResponseEntity<Serializable> responseEntity;

        try {

            ProductEntity productEntity = business.getProductById(id);
            responseEntity = ResponseEntityUtil.INSTANCE.handleSingleResponse(productEntity);

        } catch (ElementNotFoundException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }
}
