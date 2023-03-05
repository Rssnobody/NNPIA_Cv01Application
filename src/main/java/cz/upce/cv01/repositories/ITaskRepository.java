package cz.upce.cv01.repositories;

import cz.upce.cv01.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
