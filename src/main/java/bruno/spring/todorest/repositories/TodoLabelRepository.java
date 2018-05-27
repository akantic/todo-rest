package bruno.spring.todorest.repositories;

import bruno.spring.todorest.models.TodoLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoLabelRepository extends JpaRepository<TodoLabel, Long> {
}
