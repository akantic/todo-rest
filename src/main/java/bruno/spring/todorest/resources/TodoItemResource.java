package bruno.spring.todorest.resources;

import bruno.spring.todorest.models.TodoItem;
import bruno.spring.todorest.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoItemResource {
    private final TodoItemService todoService;

    @Autowired
    public TodoItemResource(TodoItemService todoService) {
        this.todoService = todoService;
    }


    @GetMapping("/todos")
    public List<TodoItem> retrieveAllTodos() {
        return todoService.findAll();
    }

    @GetMapping(value = "/todos", params = "completed")
    public List<TodoItem> retrieveFilteredTodos(@RequestParam String completed) {
        if (completed.equals("true")) {
            return todoService.findCompleted();
        } else {
            return todoService.findActive();
        }
    }

    @GetMapping("/todos/{id}")
    public TodoItem retrieveTodo(@PathVariable long id) {
        Optional<TodoItem> todo = todoService.find(id);

        if (!todo.isPresent())
            todo = null;

        return todo.get();
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/todos")
    public TodoItem createTodo(@RequestBody TodoItem todo) {
        TodoItem savedTodo = todoService.create(todo);
        return savedTodo;
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Object> updateTodo(@RequestBody TodoItem todo, @PathVariable long id) {
        Optional<TodoItem> todoOptional = todoService.find(id);
        if (!todoOptional.isPresent())
            todo = null;

        TodoItem realTodo = todoOptional.get();

        if (todo.getDescription() != null) {
            realTodo.setDescription(todo.getDescription());
        }
        if (todo.isCompleted()) {
            realTodo.setCompleted(true);
        }

        todoService.update(realTodo);
        return ResponseEntity.noContent().build();
    }
}