package br.com.enap.curso.projeto.errorHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationError extends RuntimeException {
    private final int statusCode;

    public ValidationError(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}