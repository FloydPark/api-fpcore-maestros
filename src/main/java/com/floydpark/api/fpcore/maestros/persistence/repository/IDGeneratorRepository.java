package com.floydpark.api.fpcore.maestros.persistence.repository;

import com.floydpark.api.fpcore.maestros.persistence.entity.IDSequenceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDGeneratorRepository  extends MongoRepository<IDSequenceEntity, String> {

}
