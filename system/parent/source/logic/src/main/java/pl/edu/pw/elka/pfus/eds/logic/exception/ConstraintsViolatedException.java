package pl.edu.pw.elka.pfus.eds.logic.exception;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ConstraintsViolatedException extends LogicException {
    private Set<ConstraintViolation<GenericEntity>> errors;

    public ConstraintsViolatedException(Set<ConstraintViolation<GenericEntity>> errors) {
        super(""); // myk na dziedziczenie
        this.errors = errors;
    }

    public Set<ConstraintViolation<GenericEntity>> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return errors.toString();
    }
}
