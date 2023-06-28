package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.auth.AuthenticationContext;
import br.com.enap.curso.projeto.errorHandling.AliasAlreadyExistsException;
import br.com.enap.curso.projeto.errorHandling.UrlNotCorrectException;
import br.com.enap.curso.projeto.model.AccessStatistics;
import br.com.enap.curso.projeto.model.DTO.AuthenticatedUser;
import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import br.com.enap.curso.projeto.service.AccessService;
import br.com.enap.curso.projeto.service.ShortedLinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShortedLinkAPIController {

    Logger logger = LoggerFactory.getLogger(ShortedLinkAPIController.class);

    @Autowired
    private AccessService accessService;

    @Autowired
    private ShortedLinkService shortedLinkService;

    @GetMapping("/links")
    @AuthenticationContext(userId = "#request.getUserId")
    public List<ShortedLink> getAllShortedLinks(HttpServletRequest request) {
        var authenticatedUser = (AuthenticatedUser) request.getAttribute("authenticatedUser");
        return shortedLinkService.getShortedLinksByUserId(authenticatedUser.getUserId());
    }

    @PostMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShortedLink>> postLinks(@RequestBody Object payload) throws Exception {
        /*
         * Neste método, devemos verificar se o objeto passado é ou não uma
         * lista. Para isso, não podemos receber diretamente um payload de tipo
         * List<ShortedLink>, pois será gerada uma exceção no momento da
         * passagem do parâmetro, que devolverá uma mensagem de erro pouco
         * útil através do tratamento de erros global.
         *
         * Então recebemos um objeto Object para verificar se é ou não uma
         * lista e, caso não seja, retornamos uma mensagem de erro útil para o
         * consumidor da API.
         */
        if (!(payload instanceof List))
            throw new Exception("Error: payload (JSON) should be a list of objects.");

        /*
         * Uma vez identificado o objeto como uma lista, não é possível fazer
         * um cast simples, sendo necessário utilizar o Jackson para fazer a
         * conversão do Object para List<ShortedLink>, pois a conversão
         * interna é feita para um objeto do tipo LinkedHashMap, sendo
         * necessário um processamento explícito para ser convertida para um
         * objeto do tipo desejado.
         *
         * Mais informações em:
         * https://www.baeldung.com/jackson-linkedhashmap-cannot-be-cast
         */
        ObjectMapper mapper = new ObjectMapper();
        List<ShortedLink> sLinks = mapper.convertValue(payload, new TypeReference<List<ShortedLink>>() {});

        /* Finalmente podemos salvar os links recebidos */
        validUrls(sLinks);
        shortedLinkService.saveAllShortedLinks(sLinks);
        return new ResponseEntity<List<ShortedLink>>(sLinks, HttpStatus.OK);
    }

    @PutMapping(value = "/links/alias/{alias}")
    public ResponseEntity updateLink(@PathVariable String alias, @RequestBody ShortedLink sLink) throws Exception {
        validUrls(Arrays.asList(sLink));
        shortedLinkService.updateShortedLink(sLink, alias);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/links/alias/{alias}")
    public ResponseEntity deleteLink(@PathVariable String alias) {
        logger.info(String.format("delete link by alias: {%s}", alias));
        shortedLinkService.removeShortedLinkByAlias(alias);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/analytics/{alias}")
    public AccessStatistics getAccessStatisticsByAlias(@PathVariable String alias){
        return accessService.getAccessStatisticsByAlias(alias);
    }

    public void validUrls(List<ShortedLink> sLinks) throws Exception {
        for (ShortedLink s: sLinks) {
            try {
                var sLinkExists = shortedLinkService.getShortedLinkByAlias(s.getAlias());
                if (sLinkExists != null) throw new AliasAlreadyExistsException(s.getAlias());
                new URL(s.getLink());
            } catch (MalformedURLException e) {
                throw new UrlNotCorrectException(s.getLink());
            }
        }
    }

}
