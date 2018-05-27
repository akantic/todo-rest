package bruno.spring.todorest.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String description;

    @CreationTimestamp
    private Date createdAt;

    private boolean isCompleted = false;

    private Date completedAt;

    private Date dueDate;

    @ManyToMany
    @JoinTable(
            name = "Todos_labels",
            joinColumns = { @JoinColumn(name = "todo_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id", referencedColumnName = "id") }
    )
    private List<TodoLabel> labels = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<TodoLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<TodoLabel> labels) {
        this.labels = labels;
    }

    public void addLabel(TodoLabel label) {
        if (!this.labels.contains(label)) {
            this.labels.add(label);
        }

        if (!label.getTodoItems().contains(this)) {
            label.getTodoItems().add(this);
        }
    }

    public void removeLabel(TodoLabel label) {
        this.labels.remove(label);
        label.getTodoItems().remove(this);
    }

    public TodoItem() {
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nDescription: " + description + "\nCompleted: " + isCompleted;
    }



}