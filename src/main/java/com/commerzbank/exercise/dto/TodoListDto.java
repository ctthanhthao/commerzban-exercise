package com.commerzbank.exercise.dto;

import lombok.Builder;

@Builder
public record TodoListDto(String todoList, String finishDate) {
}
