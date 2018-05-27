package bruno.spring.todorest.resources;

import bruno.spring.todorest.models.TodoItem;
import bruno.spring.todorest.models.TodoLabel;
import bruno.spring.todorest.services.TodoLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        labelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
