package cz.upce.cv01.controllers;

import cz.upce.cv01.domain.Task;
import cz.upce.cv01.dto.AppUserOutputDto;
import cz.upce.cv01.dto.TaskOutputDto;
import cz.upce.cv01.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class TaskQLController {
    private final TaskService taskService;

    @SchemaMapping(typeName = "AppUser")
    public List<TaskOutputDto> tasks(final AppUserOutputDto user) {
        return taskService.findAllByAppUserId(user.getId())
                .stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }
}
