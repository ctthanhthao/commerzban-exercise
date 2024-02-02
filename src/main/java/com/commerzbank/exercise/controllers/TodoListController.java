package com.commerzbank.exercise.controllers;


import com.commerzbank.exercise.dto.TodoListDto;
import com.commerzbank.exercise.exception.ErrorResponse;
import com.commerzbank.exercise.services.TodoListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.commerzbank.exercise.utilities.ConstantApiPath.TODO_LIST_API;

@RestController
@RequestMapping(TODO_LIST_API)
@RequiredArgsConstructor
@Tag(name = "TODO List Creation", description = "TODO List management APIs")
public class TodoListController {

    private final TodoListService todoListService;

    @Operation(
            description = "Return TODO list for today.",
            responses = {
                    @ApiResponse(
                            description = "Successfully returned result.",
                            responseCode = "200",
                            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TodoListDto.class)) }
                    ),
                    @ApiResponse(
                            description = "There is unexpected error happen.",
                            responseCode = "500",
                            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)) }
                    )
            }

    )
    @GetMapping
    public ResponseEntity<TodoListDto> getTodoList(
            @RequestParam(required = false) List<String> tasks
    ) throws ExecutionException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(
                todoListService.initializeTodoList(tasks),
                headers,
                HttpStatus.OK
        );
    }
}
