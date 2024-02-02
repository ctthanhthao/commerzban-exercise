package com.commerzbank.exercise.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskBuilderService {

    @Value("${application.todolist.default}")
    private String defaultTask;

    public String buildTasks(List<String> tasks) {
        if (CollectionUtils.isEmpty(tasks)) {
            return defaultTask;
        }
        return tasks.stream().collect(Collectors.joining(","));
    }

}
