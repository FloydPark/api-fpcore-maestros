package com.floydpark.api.fpcore.maestros.persistence.repository;

import com.floydpark.api.fpcore.maestros.persistence.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {


}
