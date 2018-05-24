package bruno.spring.todorest.services;

import bruno.spring.todorest.models.TodoItem;
import bruno.spring.todorest.repositories.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemServiceImpl implements TodoItemService {
    private final TodoItemRepository todoItemRepo;

    @Autowired
    public TodoItemServiceImpl(TodoItemRepository todoItemRepo) {
        this.todoItemRepo = todoItemRepo;
    }

    @Override
    public List<TodoItem> findAll() {
        return todoItemRepo.findAll();
    }

    @Override
    public Optional<TodoItem> find(Long id) {
        return todoItemRepo.findById(id);
    }

    @Override
    public TodoItem create(TodoItem object) {
        return todoItemRepo.save(object);
    }

    @Override
    public TodoItem update(TodoItem object) {
        return todoItemRepo.save(object);
    }

    @Override
    public void delete(Long id) {
        todoItemRepo.deleteById(id);
    }
}
