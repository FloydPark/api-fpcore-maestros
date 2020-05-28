package com.floydpark.api.fpcore.maestros.business;

import com.floydpark.api.fpcore.maestros.persistence.entity.ProductEntity;
import com.floydpark.api.fpcore.maestros.persistence.repository.ProductRepository;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductBusiness {

    private ProductRepository repository;

    public ProductEntity getProductById(String id) throws ElementNotFoundException {

        Optional<ProductEntity> product = repository.findById(id);

        if(!product.isPresent()){

            throw new ElementNotFoundException("The product does not exist");
        }

        return product.get();
    }

    public List<ProductEntity> getProducts() throws ElementNotFoundException {

        List<ProductEntity> products = repository.findAll();

        if(products == null || products.size() == 0){

            throw new ElementNotFoundException("There are no products, yet.");
        }

        return products;
    }

    public void createFooProduct(){

        ProductEntity productEntity = new ProductEntity();
        String dateTime = ""+new Date().getTime();
        productEntity.setId(dateTime);
        productEntity.setName("Product - "+dateTime);
        productEntity.setValue(5000);
        productEntity.setIva(false);
        productEntity.setUrlPhoto("");
        repository.save(productEntity);
    }
}