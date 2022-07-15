package io.starskyoio.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private T data;
    private String msg;
}
