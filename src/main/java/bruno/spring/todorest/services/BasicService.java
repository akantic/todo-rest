package bruno.spring.todorest.services;

import java.util.List;
import java.util.Optional;

public interface BasicService<T> {
    List<T> findAll();

    Optional<T> find(Long id);

    T create(T object);

    T update(T object);

    void delete(Long id);
}