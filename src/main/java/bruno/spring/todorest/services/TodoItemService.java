package bruno.spring.todorest.services;

import bruno.spring.todorest.models.TodoItem;

import java.util.List;

public interface TodoItemService extends BasicService<TodoItem>{
    List<TodoItem> findActive();

    List<TodoItem> findCompleted();
}