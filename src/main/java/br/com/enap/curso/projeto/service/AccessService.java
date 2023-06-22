package br.com.enap.curso.projeto.service;

import br.com.enap.curso.projeto.model.*;
import br.com.enap.curso.projeto.repository.AccessRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessService {

    @Autowired
    private AccessRepository repo;

    public void saveAccess(Access access){
        repo.save(access);
    }

    public AccessStatistics getAccessStatisticsByAlias(String alias){
        long totalHitsByAlias = repo.totalHitsByAlias(alias);

        List<TotalByBrowser> browsers = repo.totalHitsByBrowserByAlias(alias);

        List<TotalByLanguage> languages = repo.totalHitsByLanguageByAlias(alias);

        List<TotalByPlatform> platforms = repo.totalHitsByPlatformByAlias(alias);

        return AccessStatistics.builder()
                .totalHits(totalHitsByAlias)
                .totalByBrowsers(browsers)
                .totalByLanguages(languages)
                .totalByPlatforms(platforms)
                .build();
    }
}
