package br.com.enap.curso.projeto.errorHandling;

public class UrlNotCorrectException extends Exception {
    public UrlNotCorrectException(String url) {
        super(String.format("the url '%s' is malformed", url));
    }

}
