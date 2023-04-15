package cz.upce.cv01.controllers;

import cz.upce.cv01.domain.Task;
import cz.upce.cv01.dto.TaskOutputDto;
import cz.upce.cv01.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        var result = taskService.findAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping(path="/{userId}")
    public ResponseEntity<?> findByUser(@PathVariable final Long userId) {
        var result = taskService.findAllByAppUserId(userId)
                .stream()
                .map(Task::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }
}
