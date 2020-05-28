package com.floydpark.api.fpcore.maestros.business;

import com.floydpark.api.fpcore.maestros.commons.dto.ProductRequestDTO;
import com.floydpark.api.fpcore.maestros.persistence.entity.ProductEntity;
import com.floydpark.api.fpcore.maestros.persistence.repository.ProductRepository;
import com.floydpark.lib.commons.exception.BusinessException;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import com.floydpark.lib.commons.exception.ValidationException;
import com.floydpark.lib.commons.util.DozerUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductBusiness {

    private ProductRepository repository;

    private Validator validator;

    public ProductEntity getProductById(String id) throws ElementNotFoundException {

        Optional<ProductEntity> product = repository.findById(id);

        if(!product.isPresent()){

            throw new ElementNotFoundException("The product does not exist");
        }

        return product.get();
    }

    public List<ProductEntity> getProducts() throws ElementNotFoundException {

        List<ProductEntity> products = repository.findAll();

        if(products.isEmpty()){

            throw new ElementNotFoundException("There are no products, yet.");
        }

        return products;
    }

    public ProductEntity addProduct(ProductRequestDTO productRequestDTO) throws ValidationException, BusinessException{

        validateProductRequest(productRequestDTO);
        if(repository.existsById(productRequestDTO.getId())){

            throw new BusinessException("The product with ID "+productRequestDTO.getId()+" already exists.");
        }
        ProductEntity productEntity = DozerUtil.INSTANCE.getMapper().map(productRequestDTO, ProductEntity.class);
        productEntity = repository.save(productEntity);
        return productEntity;
    }

    private void validateProductRequest(ProductRequestDTO productRequestDTO) throws ValidationException {

        List<String> errors = new ArrayList<>();

        if(productRequestDTO != null){

            Set<ConstraintViolation<ProductRequestDTO>> violations = validator.validate(productRequestDTO);

            if(!violations.isEmpty()){

                violations.forEach( constraintViolation -> errors.add(constraintViolation.getMessage()));
            }
        }
        else
        {
            errors.add("There is no information to process.");
        }

        if(!errors.isEmpty()){

            throw new ValidationException("Invalid product information.", errors);
        }
    }
}
