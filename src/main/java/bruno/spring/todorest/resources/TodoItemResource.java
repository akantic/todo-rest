package bruno.spring.todorest.resources;

import bruno.spring.todorest.models.TodoItem;
import bruno.spring.todorest.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/todos/{id}")
    public TodoItem retrieveTodo(@PathVariable long id) {
        Optional<TodoItem> todo = todoService.find(id);

        if (!todo.isPresent())
            todo = null;

        return todo.get();
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable long id) {
        todoService.delete(id);
    }

    @PostMapping("/todos")
    public ResponseEntity<Object> createTodo(@RequestBody TodoItem todo) {
        TodoItem savedTodo = todoService.create(todo);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTodo.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody TodoItem todo, @PathVariable long id) {
        Optional<TodoItem> todoOptional = todoService.find(id);

        if (!todoOptional.isPresent())
            todo = null;

        todo.setId(id);
        todoService.update(todo);
        return ResponseEntity.noContent().build();
    }
}