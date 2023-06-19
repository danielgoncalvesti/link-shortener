package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class ShortedLinkController {
    @Autowired
    private final ShortedLinkRepository repo;

    public ShortedLinkController(ShortedLinkRepository repo) {
        this.repo = repo;
    }

    @GetMapping(value = "/notfound/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity notfound(@PathVariable String alias){
        String message = "there is no link for the following alias: " + alias;
        return ResponseEntity.ok().body(message);
    }

    @GetMapping(value = "/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView redirect(@PathVariable String alias){
        ShortedLink sLink = repo.findByAlias(alias);
        if(sLink != null) {
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
