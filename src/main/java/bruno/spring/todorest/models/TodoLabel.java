package bruno.spring.todorest.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoLabel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String value;

    @ManyToMany(mappedBy = "labels")
    @JsonIgnore
    private List<TodoItem> todoItems = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public void addTodo(TodoItem todo) {
        if(!this.todoItems.contains(todo)) {
            this.todoItems.add(todo);
        }

        if(!todo.getLabels().contains(this)) {
            todo.getLabels().add(this);
        }
    }

    public void removeTodo(TodoItem todo) {
        this.todoItems.remove(todo);
        todo.getLabels().remove(this);
    }

    public TodoLabel() {

    }

    @Override
    public String toString() {
        return "Id: " + id + "\nValue: " + value + "\n";
    }


}
