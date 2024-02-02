package com.commerzbank.exercise.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TaskBuilderServiceUnitTests {

    @InjectMocks
    private TaskBuilderService taskBuilderService;

    @Test
    public void testBuildTaskList() {
        List<String> tasks = List.of("swimming,meeting,imagining");
        String expected = String.join(",", tasks);
        String result = taskBuilderService.buildTasks(tasks);
        Assertions.assertEquals(expected, result);
    }

}
