package bruno.spring.todorest.models.validators;


import bruno.spring.todorest.models.TodoItem;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TodoItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TodoItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TodoItem todo = (TodoItem) o;

        if (todo.getDescription() == null) {
            errors.rejectValue("description", "description must exist");
        }

        if (todo.getDescription().length() < 3 || todo.getDescription().length() > 100) {
            errors.rejectValue("description", "description length must be between 3 and 100");
        }

        if (todo.getDueDate() == null) {
            errors.rejectValue("description", "due date must exist");
        }

        if (todo.getLabels().size() > 3) {
            errors.rejectValue("labels", "a todo item can not have more than 3 labels");
        }
    }
}
