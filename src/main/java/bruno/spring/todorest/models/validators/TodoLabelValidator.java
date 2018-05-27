package bruno.spring.todorest.models.validators;


import bruno.spring.todorest.models.TodoLabel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TodoLabelValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TodoLabel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TodoLabel label = (TodoLabel) o;

        if (label.getValue() == null) {
            errors.rejectValue("value", "value must exist");
        }

    }
}
