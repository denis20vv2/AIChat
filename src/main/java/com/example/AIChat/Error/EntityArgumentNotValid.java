package com.example.AIChat.Error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class EntityArgumentNotValid {

    public EntityArgumentNotValid(LocalDateTime timestamp, int status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    private LocalDateTime timestamp;
    private int status;
    private String error;
    // private String message;
    private String path;
    private Map<String, String> fieldErrors;

}
