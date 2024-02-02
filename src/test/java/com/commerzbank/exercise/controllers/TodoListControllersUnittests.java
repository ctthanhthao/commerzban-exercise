package com.commerzbank.exercise.controllers;

import com.commerzbank.exercise.dto.TodoListDto;
import com.commerzbank.exercise.services.TodoListService;
import com.commerzbank.exercise.utilities.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerzbank.exercise.utilities.ConstantApiPath.CONTEXT_PATH;
import static com.commerzbank.exercise.utilities.ConstantApiPath.TODO_LIST_API;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = TodoListController.class)
public class TodoListControllersUnittests {

    @MockBean
    private TodoListService todoListService;
    @Autowired
    private MockMvc mockMvc;

    private static final String TODO_LIST_API_PATH = CONTEXT_PATH + TODO_LIST_API;
    private static final List<String> tasks = Arrays.asList("playing","coding","studying","eating");

    private static Collection<Object[]> parametersTest() {
        return Arrays.asList(new Object[][] {
                {false, true, tasks},
                {true, false, null}
        });
    }

    @ParameterizedTest
    @MethodSource("parametersTest")
    public void getTodoListCreatedForToday(boolean defaultList, boolean hasQueryParam, List<String> tasks) throws Exception {
        TodoListDto todoList = createTodoListTest(defaultList);
        when(todoListService.initializeTodoList(tasks)).thenReturn(todoList);
        MockHttpServletRequestBuilder requestBuilder = get(TODO_LIST_API_PATH);
        if(hasQueryParam) {
            tasks.stream().forEach(t -> requestBuilder.queryParam("tasks", t));
        }
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.todoList", is(todoList.todoList())))
                .andExpect(jsonPath("$.finishDate", is(todoList.finishDate())));
    }

    @Test
    public void getTodoListCreatedForTodayMeetException() throws Exception {
        doThrow(new TestException()).when(todoListService).initializeTodoList(null);
        mockMvc.perform(get(TODO_LIST_API_PATH))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.path", is(TODO_LIST_API_PATH)))
                .andExpect(jsonPath("$.message", is("There is an unexpected error.")));
    }

    private TodoListDto createTodoListTest(Boolean isDefault) {
        if (isDefault)
            return TodoListDto
                    .builder()
                    .todoList(tasks.get(0))
                    .finishDate(DateUtils.formatTimeToUTC1inYYYYMMdd(LocalDateTime.now()))
                    .build();
        else
            return TodoListDto
                    .builder()
                    .finishDate(DateUtils.formatTimeToUTC1inYYYYMMdd(LocalDateTime.now()))
                    .todoList(tasks.stream().collect(Collectors.joining(",")))
                    .build();
    }
}

class TestException extends RuntimeException {
    public TestException() {
        super("There is an unexpected error.");
    }
}
