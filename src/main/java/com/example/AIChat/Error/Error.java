package com.example.AIChat.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Error {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
