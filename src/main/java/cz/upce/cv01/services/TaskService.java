package cz.upce.cv01.services;

import cz.upce.cv01.domain.Task;
import cz.upce.cv01.repositories.ITaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final ITaskRepository taskRepository;

    public List<Task> findAllByAppUserId(final Long id) {
        return taskRepository.findAllByAuthorId(id);
    }

    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }
}
