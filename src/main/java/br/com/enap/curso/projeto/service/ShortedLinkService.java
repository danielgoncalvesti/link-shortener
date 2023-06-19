package br.com.enap.curso.projeto.service;

import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShortedLinkService {

    @Autowired
    ShortedLinkRepository repo;

    public void updateShortedLink(ShortedLink s, String alias) {
        ShortedLink shortedLink = repo.findByAlias(alias);

        shortedLink.setLink(s.getLink());
        repo.save(shortedLink);
    }

    public void saveAllShortedLinks(List<ShortedLink> shortedLinks) {
        for (ShortedLink s: shortedLinks){
            if(s.getAlias() == null) {
                s.setAlias(UUID.randomUUID().toString().substring(0, 5));
            }
            if(s.getIsprivate() == null) {
                s.setIsprivate(false);
            }
        }
        repo.saveAll(shortedLinks);
    }

    public void saveShortedLink(ShortedLink shortedLink) {
        repo.save(shortedLink);
    }
}
