package br.com.enap.curso.projeto.errorHandling;

import br.com.enap.curso.projeto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TratamentoErroGlobal {

    Logger logger = LoggerFactory.getLogger(TratamentoErroGlobal.class);

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseMessage> errorHandling(Exception ex, WebRequest request) {
        logger.error(String.format("Exception message: %s", ex.getMessage()));
        return ResponseEntity.status(500).body(
                new ResponseMessage(ex.getMessage())
        );
    }

    @ExceptionHandler(ValidationError.class)
    protected ResponseEntity<ResponseMessage> validationErrorHandling(Exception ex, WebRequest request) {
        logger.error(String.format("ValidationError message: %s", ex.getMessage()));
        ValidationError validationError = (ValidationError) ex;
        return ResponseEntity.status(validationError.getStatusCode()).body(
                new ResponseMessage(ex.getMessage())
        );
    }

    @ExceptionHandler(UrlNotCorrectException.class)
    protected ResponseEntity<ResponseMessage> urlNotCorrectHandling(Exception ex, WebRequest request) {
        logger.error(String.format("UrlNotCorrectException message: %s", ex.getMessage()));
        return ResponseEntity.status(400).body(
                new ResponseMessage(ex.getMessage())
        );
    }

    @ExceptionHandler(AliasAlreadyExistsException.class)
    protected ResponseEntity<ResponseMessage> aliasAlreadyExistsHandling(Exception ex, WebRequest request) {
        logger.error(String.format("AliasAlreadyExistsException message: %s", ex.getMessage()));
        return ResponseEntity.status(400).body(
                new ResponseMessage(ex.getMessage())
        );
    }
}
