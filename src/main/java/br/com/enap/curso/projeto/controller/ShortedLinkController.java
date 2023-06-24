package br.com.enap.curso.projeto.controller;

import br.com.enap.curso.projeto.model.Access;
import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.service.AccessService;
import br.com.enap.curso.projeto.service.ShortedLinkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log
@RestController
public class ShortedLinkController {
    @Autowired
    private ShortedLinkService shortedLinkService;

    @Autowired
    private AccessService accessService;

    // TODO: adicionar todos os browsers existentes
    private Map<String, String> BROWSERS_LIST = new HashMap<String, String>(){{
        put("Chromium", "Chrome");
        put("GoogleChrome", "Chrome");
        put("Firefox", "Firefox");
        put("Mozilla", "Firefox");
    }};


    @GetMapping(value = "/notfound/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity notfound(@PathVariable String alias){
        String message = "there is no link for the following alias: " + alias;
        return ResponseEntity.ok().body(message);
    }

    private Access builtAccess(Map<String,String> headers, String ip, ShortedLink sLink){
        Map<String,String> headerResult=new HashMap<>();
        headers.forEach((header, val) -> {
            log.info(String.format("header: %s value: %s" ,header, val));
            if (header.equals("sec-ch-ua")) {
                String value  = Arrays.stream(val.substring(1).split(","))
                        .map(str -> str.replaceAll("\\s+", "")
                                .replaceAll("\"", "").split(";")[0])
                        .filter(x -> BROWSERS_LIST.containsKey(x)).limit(1).toList().get(0);
                value = BROWSERS_LIST.get(value);
                headerResult.put("browser", value);
            } else if (header.equals("sec-ch-ua-platform")) {
                String value = val.split(";")[0].replaceAll("\"", "");
                headerResult.put("os", value);
            } else if (header.equals("accept-language")) {
                String value = val.split(",")[0].replaceAll("\"", "");
                headerResult.put("language", value);
            } else if (header.equals("user-agent")) {
                String value = val;
                headerResult.put("user-agent", value);
            }
        });
        if (headerResult.get("browser") == null){
            // verifica se tem o atributo browser no user-agent
            String userAgent = headerResult.getOrDefault("user-agent", "");
            String findBrowser = null;
            for (Map.Entry<String, String> entry : BROWSERS_LIST.entrySet()){
                if (userAgent.split(entry.getKey()).length > 0 && findBrowser == null) {
                    findBrowser = entry.getKey();
                    headerResult.put("browser", entry.getValue());
                    break;
                }
            }
        }
        return Access.builder()
                .browser(headerResult.getOrDefault("browser", "other"))
                .shortedLink(sLink)
                .platform(headerResult.getOrDefault("os", "other"))
                .language(headerResult.getOrDefault("language", "other"))
                .userAgentData(headerResult.getOrDefault("user-agent", "other"))
                .ipAccess(ip)
                .build();
    }

    @GetMapping(value = "/{alias}", produces = MediaType.TEXT_HTML_VALUE)
    public RedirectView redirect(@PathVariable String alias, @RequestHeader Map<String, String> headers, HttpServletRequest request){
        ShortedLink sLink = shortedLinkService.getShortedLinkByAlias(alias);

        if(sLink != null) {
            accessService.saveAccess(builtAccess(headers, request.getRemoteAddr(), sLink));
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
