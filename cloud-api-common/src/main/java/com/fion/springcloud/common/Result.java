package com.fion.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public static Result wrapSuccess() {
        return Result.builder().code(200).message(null).data(null).build();
    }

    public static <T> Result wrapSuccess(T data) {
        return Result.builder().code(200).message(null).data(data).build();
    }

    public static <T> Result wrapSuccess(T data, String message) {
        return Result.builder().code(200).message(message).data(data).build();
    }

    public static Result wrapError(Integer code, String message) {
        return Result.builder().code(code).message(message).data(null).build();
    }
}
