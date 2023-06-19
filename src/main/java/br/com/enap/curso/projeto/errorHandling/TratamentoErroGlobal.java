package br.com.enap.curso.projeto.errorHandling;

import br.com.enap.curso.projeto.ReturnMessage;
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
    protected ResponseEntity<ReturnMessage> errorHandling(Exception ex, WebRequest request) {
        logger.error(String.format("Exception message: %s", ex.getMessage()));
        return ResponseEntity.status(500).body(
                new ReturnMessage(ex.getMessage())
        );
    }

    @ExceptionHandler(UrlNotCorrectException.class)
    protected ResponseEntity<ReturnMessage> urlNotCorrectHandling(Exception ex, WebRequest request) {
        logger.error(String.format("UrlNotCorrectException message: %s", ex.getMessage()));
        return ResponseEntity.status(400).body(
                new ReturnMessage(ex.getMessage())
        );
    }

    @ExceptionHandler(AliasAlreadyExistsException.class)
    protected ResponseEntity<ReturnMessage> aliasAlreadyExistsHandling(Exception ex, WebRequest request) {
        logger.error(String.format("AliasAlreadyExistsException message: %s", ex.getMessage()));
        return ResponseEntity.status(400).body(
                new ReturnMessage(ex.getMessage())
        );
    }
}
