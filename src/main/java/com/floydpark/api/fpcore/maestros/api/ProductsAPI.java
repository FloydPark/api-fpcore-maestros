package com.floydpark.api.fpcore.maestros.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsAPI {

    @GetMapping("/")
    public ResponseEntity getProducts(){

        return ResponseEntity.ok("OK Products");
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable("id")String id){

        ResponseEntity responseEntity = null;
        if(StringUtils.isEmpty(id)){

            responseEntity = new ResponseEntity("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        else
        {
            responseEntity = ResponseEntity.ok("Product ID -> "+id);
        }
        return responseEntity;
    }
}
