package com.erp.commonlib.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String message;
    private String path;
    private String errorCode;
    private LocalDateTime timestamp;

}
