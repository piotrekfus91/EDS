package pl.edu.pw.elka.pfus.eds.logic.exception;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;

import javax.validation.ConstraintViolation;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ConstraintsViolatedException extends LogicException {
    private static final String ERROR_MESSAGE = "Wpisane dane są niepoprawne";

    private Set<ConstraintViolation<GenericEntity>> errors;

    public ConstraintsViolatedException(Set<ConstraintViolation<GenericEntity>> errors) {
        super(ERROR_MESSAGE);
        this.errors = errors;
    }

    public List<String> getErrors() {
        List<String> constraintViolations = new LinkedList<>();
        for(ConstraintViolation<GenericEntity> constraintViolation : errors) {
            constraintViolations.add(constraintViolation.getMessage());
        }
        return constraintViolations;
    }
}
