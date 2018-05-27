package bruno.spring.todorest.services;

import bruno.spring.todorest.models.TodoLabel;
import bruno.spring.todorest.repositories.TodoLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoLabelServiceImpl implements TodoLabelService {
    private final TodoLabelRepository todoLabelRepo;

    @Autowired
    public TodoLabelServiceImpl(TodoLabelRepository todoLabelRepo) {
        this.todoLabelRepo = todoLabelRepo;
    }

    @Override
    public List<TodoLabel> findAll() {
        return todoLabelRepo.findAll();
    }

    @Override
    public Optional<TodoLabel> find(Long id) {
        return todoLabelRepo.findById(id);
    }

    @Override
    public TodoLabel create(TodoLabel object) {
        return todoLabelRepo.save(object);
    }

    @Override
    public TodoLabel update(TodoLabel object) {
        return todoLabelRepo.save(object);
    }

    @Override
    public void delete(Long id) {
        todoLabelRepo.deleteById(id);
    }
}
