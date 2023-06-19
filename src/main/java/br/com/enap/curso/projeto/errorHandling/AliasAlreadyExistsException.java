package br.com.enap.curso.projeto.errorHandling;

public class AliasAlreadyExistsException extends Exception {
    public AliasAlreadyExistsException(String url) {
        super(String.format("the alias '%s' already exists", url));
    }
}
