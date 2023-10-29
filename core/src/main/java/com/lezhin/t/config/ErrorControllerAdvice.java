package com.lezhin.t.config;

import com.lezhin.t.response.LezhinErrorResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler({Exception.class, RuntimeException.class, InterruptedException.class})
    public LezhinErrorResponse<ErrorResponseDto> handleAllExceptions(Exception ex) {
        String errorType;
        String errorMessage;

        if (ex != null && ex.toString().length() > 0 && StringUtils.split(ex.toString(), ": ").length > 0) {
            String[] split = StringUtils.split(ex.toString(), ": ");

            errorType = split[0];
            errorMessage = split[1];
        } else {
            errorType = "error";
            errorMessage = "error";
        }

        log.error("errorType : {}", errorType);
        log.error("errorMessage : {}", errorMessage);

        return LezhinErrorResponse.<ErrorResponseDto>builder()
                .data(ErrorResponseDto.builder()
                        .errorType(errorType)
                        .message(errorMessage)
                        .build())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorResponseDto {
        private String errorType;
        private String message;
    }

}
