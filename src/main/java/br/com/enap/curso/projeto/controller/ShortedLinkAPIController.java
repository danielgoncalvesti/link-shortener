package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.errorHandling.AliasAlreadyExistsException;
import br.com.enap.curso.projeto.errorHandling.UrlNotCorrectException;
import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import br.com.enap.curso.projeto.service.ShortedLinkService;
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
    private final ShortedLinkRepository repo;

    @Autowired
    private ShortedLinkService shortedLinkService;

    public ShortedLinkAPIController(ShortedLinkRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/links")
    public List<ShortedLink> getAllShortedLinks() {
        return repo.findAll();
    }

    @PostMapping(value = "/links", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShortedLink>> postLinks(@RequestBody List<ShortedLink> sLinks) throws Exception {
        validUrls(sLinks);
        shortedLinkService.saveAllShortedLinks(sLinks);
        repo.saveAll(sLinks);
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
        repo.deleteByAlias(alias);
        return ResponseEntity.noContent().build();
    }

    public void validUrls(List<ShortedLink> sLinks) throws Exception {
        for (ShortedLink s: sLinks) {
            try {
                var sLinkExists = repo.findByAlias(s.getAlias());
                if (sLinkExists != null) throw new AliasAlreadyExistsException(s.getAlias());
                new URL(s.getLink());
            } catch (MalformedURLException e) {
                throw new UrlNotCorrectException(s.getLink());
            }
        }
    }
}
