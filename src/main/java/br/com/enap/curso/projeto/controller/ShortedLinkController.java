package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.model.Access;
import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.repository.AccessRepository;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Log
@RestController
public class ShortedLinkController {
    @Autowired
    private final ShortedLinkRepository repo;

    @Autowired
    private AccessRepository accessRepository;

    public ShortedLinkController(ShortedLinkRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/notfound/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity notfound(@PathVariable String alias){
        String message = "there is no link for the following alias: " + alias;
        return ResponseEntity.ok().body(message);
    }

    @GetMapping(value = "/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView redirect(@PathVariable String alias, @RequestHeader Map<String, String> headers){
        ShortedLink sLink = repo.findByAlias(alias);
        Map<String,String> headerValues=new HashMap<>();
        if(sLink != null) {
            headers.forEach((header, val) -> {
                        if (header.equals("sec-ch-ua")) {
                            String value = val.split(";")[0].replaceAll("\"", "");
                            headerValues.put("browser", value);
                        } else if (header.equals("sec-ch-ua-platform")) {
                            String value = val.split(";")[0].replaceAll("\"", "");
                            headerValues.put("os", value);
                        } else if (header.equals("accept-language")) {
                            String value = val.split(",")[0].replaceAll("\"", "");
                            headerValues.put("language", value);
                        }
                    });
            Access access= Access.builder().
                    browser(headerValues.get("browser")).
                    shortedLink(sLink).
                    platform(headerValues.get("os")).
                    language(headerValues.get("language"))
                    .build();
            accessRepository.save(access);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(sLink.getLink());
            return redirectView;
        } else {
            RedirectView redirectViewErro = new RedirectView();
            redirectViewErro.setUrl(String.format("/notfound/%s", alias));
            return redirectViewErro;
        }
    }
}
