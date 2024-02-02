package com.commerzbank.exercise.services;

import com.commerzbank.exercise.dto.TodoListDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoListServiceUnitTests {

    @InjectMocks
    private TodoListService todoListService;
    @Mock
    private TaskBuilderService taskBuilderService;
    @Mock
    private DateService dateService;

    @Test
    public void returnTodoListDto() throws Exception {
        // given
        List<String> tasks = List.of("playing football", "dancing a song");
        String expectedTasks = String.join(",", tasks);
        String expectedDate = "2024-02-02";
        when(taskBuilderService.buildTasks(anyList())).thenReturn(expectedTasks);
        when(dateService.getCurrentDate()).thenReturn(expectedDate);
        // when
        TodoListDto dto = todoListService.initializeTodoList(tasks);
        // then
        Assertions.assertEquals(expectedTasks, dto.todoList());
        Assertions.assertEquals(expectedDate, dto.finishDate());
    }

}
