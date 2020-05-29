package com.floydpark.api.fpcore.maestros.business;

import com.floydpark.api.fpcore.maestros.commons.dto.IDSequenceRequestDTO;
import com.floydpark.api.fpcore.maestros.commons.dto.NextIDSequenceDTO;
import com.floydpark.api.fpcore.maestros.persistence.entity.IDSequenceEntity;
import com.floydpark.api.fpcore.maestros.persistence.repository.IDGeneratorRepository;
import com.floydpark.lib.commons.exception.BusinessException;
import com.floydpark.lib.commons.exception.ElementNotFoundException;
import com.floydpark.lib.commons.exception.ValidationException;
import com.floydpark.lib.commons.util.DozerUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class IDGeneratorBusiness {

    private IDGeneratorRepository repository;

    private Validator validator;

    public NextIDSequenceDTO getNextIDSequenceByPrefix(String prefix) throws ValidationException, ElementNotFoundException {

        validatePrefix(prefix);
        Optional<IDSequenceEntity> idSequenceEntity = repository.findById(prefix);

        if(!idSequenceEntity.isPresent()){

            throw new ElementNotFoundException("The ID Sequence with prefix "+prefix+" does not exist.");
        }

        IDSequenceEntity rowIdSequenceEntity = idSequenceEntity.get();
        rowIdSequenceEntity.setSequence(rowIdSequenceEntity.getSequence()+1);
        repository.save(rowIdSequenceEntity);
        return buildIDSequence(rowIdSequenceEntity);
    }

    public IDSequenceEntity getIDSequenceByPrefix(String prefix) throws ValidationException, ElementNotFoundException {

        validatePrefix(prefix);
        Optional<IDSequenceEntity> idSequenceEntity = repository.findById(prefix);

        if(!idSequenceEntity.isPresent()){

            throw new ElementNotFoundException("The ID Sequence with prefix "+prefix+" does not exist.");
        }

        return idSequenceEntity.get();
    }

    public IDSequenceEntity addIDSequence(IDSequenceRequestDTO idSequenceRequestDTO) throws ValidationException, BusinessException {

        validateIDSequenceRequest(idSequenceRequestDTO);
        if(repository.existsById(idSequenceRequestDTO.getPrefix())){

            throw new BusinessException("The ID Sequence with prefix "+idSequenceRequestDTO.getPrefix()+" already exists.");
        }
        IDSequenceEntity idSequenceEntity = DozerUtil.INSTANCE.getMapper().map(idSequenceRequestDTO, IDSequenceEntity.class);
        idSequenceEntity.setSequence(0);
        idSequenceEntity.setCreationDate(LocalDate.now());
        idSequenceEntity = repository.save(idSequenceEntity);
        return idSequenceEntity;
    }

    private void validateIDSequenceRequest(IDSequenceRequestDTO idSequenceRequestDTO) throws ValidationException {

        List<String> errors = new ArrayList<>();

        if(idSequenceRequestDTO != null){

            Set<ConstraintViolation<IDSequenceRequestDTO>> violations = validator.validate(idSequenceRequestDTO);

            if(!violations.isEmpty()){

                violations.forEach( constraintViolation -> errors.add(constraintViolation.getMessage()));
            }
        }
        else
        {
            errors.add("There is no information to process.");
        }

        if(!errors.isEmpty()){

            throw new ValidationException("Invalid ID Sequence information.", errors);
        }
    }

    private void validatePrefix(String prefix) throws ValidationException {

        List<String> errors = new ArrayList<>();

        if(StringUtils.isEmpty(prefix)){

            errors.add("The prefix should not be empty.");
        }

        if(!StringUtils.isAlpha(prefix)){

            errors.add("The prefix should have only letters.");
        }

        if(!errors.isEmpty()){

            throw new ValidationException("The prefix is invalid.", errors);
        }
    }

    private NextIDSequenceDTO buildIDSequence(IDSequenceEntity idSequenceEntity){

        String idSequence = null;
        StringBuilder idSequenceSB = new StringBuilder();
        idSequenceSB.append(idSequenceEntity.getPrefix());
        int maskLength = idSequenceEntity.getMask().length();
        String sequenceString = idSequenceEntity.getSequence()+"";
        int sequenceLength = sequenceString.length();

        if(sequenceLength > maskLength){
            idSequenceSB.append(idSequenceEntity.getSequence());
            idSequence = idSequenceSB.toString();
        }
        else
        {
            idSequenceSB.append(idSequenceEntity.getMask());
            idSequence = idSequenceSB.toString();
            idSequence = idSequence.substring(0, idSequence.length() - sequenceLength)+idSequenceEntity.getSequence();
        }

        NextIDSequenceDTO nextIDSequenceDTO = new NextIDSequenceDTO();
        nextIDSequenceDTO.setNextID(idSequence);
        return nextIDSequenceDTO;
    }
}
