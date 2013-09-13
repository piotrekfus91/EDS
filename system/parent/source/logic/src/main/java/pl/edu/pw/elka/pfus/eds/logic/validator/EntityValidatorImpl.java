package pl.edu.pw.elka.pfus.eds.logic.validator;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.validator.EntityValidator;
import pl.edu.pw.elka.pfus.eds.logic.exception.ConstraintsViolatedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class EntityValidatorImpl implements EntityValidator {
    public void validate(GenericEntity entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<GenericEntity>> errors = validator.validate(entity);
        if(!errors.isEmpty())
            throw new ConstraintsViolatedException(errors);
    }
}
