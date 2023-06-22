package br.com.enap.curso.projeto;

import br.com.enap.curso.projeto.controller.ShortedLinkAPIController;
import br.com.enap.curso.projeto.errorHandling.AliasAlreadyExistsException;
import br.com.enap.curso.projeto.errorHandling.UrlNotCorrectException;
import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.service.ShortedLinkService;
import br.com.enap.curso.projeto.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class RNServiceTest {

    @Autowired
    ShortedLinkService shortedLinkService;

    @Autowired
    UserService userService;

    @Autowired
    ShortedLinkAPIController shortedLinkAPIController;


    @Test
    void verifica_exception_urls() throws Exception {

        ShortedLink sLink=new ShortedLink();
        //url nula
        sLink.setLink("");
        Exception erro=
                Assertions.assertThrows(UrlNotCorrectException.class,() -> {
                    shortedLinkAPIController.validUrls(Arrays.asList(sLink));
                });
        //url sem http
        sLink.setLink("google.com");
        erro=
                Assertions.assertThrows(UrlNotCorrectException.class,() -> {
                    shortedLinkAPIController.validUrls(Arrays.asList(sLink));
                });

        sLink.setLink("https://google.com.br");
        sLink.setAlias("google-br");
        erro=
                Assertions.assertThrows(AliasAlreadyExistsException.class,() -> {
                    shortedLinkAPIController.validUrls(Arrays.asList(sLink));
                });


    }
}
