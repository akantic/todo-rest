package bruno.spring.todorest.resources;

import bruno.spring.todorest.models.TodoItem;
import bruno.spring.todorest.models.TodoLabel;
import bruno.spring.todorest.services.TodoLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoLabelResource {
    private final TodoLabelService labelService;

    @Autowired
    public TodoLabelResource(TodoLabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/labels")
    public List<TodoLabel> retrieveAllLabels() {
        return labelService.findAll();
    }

    @PostMapping("/labels")
    public TodoLabel createLabel(@RequestBody TodoLabel label) {
        return labelService.create(label);
    }

    @DeleteMapping("/labels/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable long id) {
        Optional<TodoLabel> label = labelService.find(id);
        if (label.isPresent()) {
            if (label.get().getTodoItems().size() == 0) {
                labelService.delete(id);
            }
        }
        return ResponseEntity.noContent().build();
    }
}
