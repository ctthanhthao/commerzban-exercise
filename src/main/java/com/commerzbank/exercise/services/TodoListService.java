package com.commerzbank.exercise.services;

import com.commerzbank.exercise.dto.TodoListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class TodoListService {

    private final DateService dateService;
    private final TaskBuilderService taskBuilderService;

    public TodoListDto initializeTodoList(List<String> tasks) throws ExecutionException, InterruptedException {
        CompletableFuture<String> todoListFuture = CompletableFuture.supplyAsync(() -> taskBuilderService.buildTasks(tasks)
        );

        CompletableFuture<String> dateFuture = CompletableFuture.supplyAsync(dateService::getCurrentDate);
        BiFunction<String, String, TodoListDto> combineFunction = (r1, r2) ->
                TodoListDto
                        .builder()
                        .todoList(r1)
                        .finishDate(r2)
                        .build();

        CompletableFuture<TodoListDto> combineFuture = todoListFuture.thenCombine(dateFuture, combineFunction);
        return combineFuture.get();
    }

}
