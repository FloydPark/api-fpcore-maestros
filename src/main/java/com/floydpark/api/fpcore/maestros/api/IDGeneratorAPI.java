package com.floydpark.api.fpcore.maestros.api;

import com.floydpark.api.fpcore.maestros.business.IDGeneratorBusiness;
import com.floydpark.api.fpcore.maestros.commons.dto.IDSequenceRequestDTO;
import com.floydpark.api.fpcore.maestros.commons.dto.NextIDSequenceDTO;
import com.floydpark.api.fpcore.maestros.persistence.entity.IDSequenceEntity;
import com.floydpark.lib.commons.exception.BusinessException;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import com.floydpark.lib.commons.exception.ValidationException;
import com.floydpark.lib.commons.util.ResponseEntityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequestMapping("/v1/idgenerator")
@AllArgsConstructor
public class IDGeneratorAPI {

    private IDGeneratorBusiness business;

    @GetMapping("/{prefix}/next")
    public ResponseEntity<Serializable> getNextIDSequenceByPrefix(@PathVariable("prefix") String prefix) {

        ResponseEntity<Serializable> responseEntity;

        try {

            NextIDSequenceDTO nextIDSequenceDTO  = business.getNextIDSequenceByPrefix(prefix);
            responseEntity = ResponseEntityUtil.INSTANCE.handleSingleResponse(nextIDSequenceDTO);

        } catch (ElementNotFoundException | ValidationException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }

    @GetMapping("/{prefix}")
    public ResponseEntity<Serializable> getIDSequenceByPrefix(@PathVariable("prefix") String prefix) {

        ResponseEntity<Serializable> responseEntity;

        try {

            IDSequenceEntity idSequenceEntity = business.getIDSequenceByPrefix(prefix);
            responseEntity = ResponseEntityUtil.INSTANCE.handleSingleResponse(idSequenceEntity);

        } catch (ElementNotFoundException | ValidationException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }

    @PostMapping("/")
    public ResponseEntity<Serializable> addIDSequenceRequest(@RequestBody IDSequenceRequestDTO idSequenceRequestDTO) {

        ResponseEntity<Serializable> responseEntity;

        try {

            IDSequenceEntity idSequenceEntity = business.addIDSequence(idSequenceRequestDTO);
            responseEntity = ResponseEntityUtil.INSTANCE.handleSingleResponse(idSequenceEntity, HttpStatus.CREATED);

        } catch (ValidationException | BusinessException e){

            responseEntity = ResponseEntityUtil.INSTANCE.handleException(e);
        }

        return responseEntity;
    }
}
