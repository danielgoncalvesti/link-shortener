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
    ShortedLinkRepository shortedLinkRepository;

    public void updateShortedLink(ShortedLink s, String alias) {
        ShortedLink shortedLink = shortedLinkRepository.findByAlias(alias);

        shortedLink.setLink(s.getLink());
        shortedLinkRepository.save(shortedLink);
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
        shortedLinkRepository.saveAll(shortedLinks);
    }

    public void saveShortedLink(ShortedLink shortedLink) {
        shortedLinkRepository.save(shortedLink);
    }

    public ShortedLink getShortedLinkByAlias(String alias){
        return shortedLinkRepository.findByAlias(alias);
    }

    public List<ShortedLink> getAllShortedLinks(){
        return shortedLinkRepository.findAll();
    }

    public List<ShortedLink> getShortedLinksByUserId(Long userId){
        return shortedLinkRepository.getAllShortedLinkByUserId(userId);
    }

    public List<ShortedLink> getShortedLinksByUser(Long userId){
        return shortedLinkRepository.findAll();
    }

    public void removeShortedLinkByAlias(String alias){
        shortedLinkRepository.deleteByAlias(alias);
    }
}
