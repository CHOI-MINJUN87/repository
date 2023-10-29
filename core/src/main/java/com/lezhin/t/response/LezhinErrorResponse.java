package com.lezhin.t.response;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LezhinErrorResponse<T> {
    T data;
}
