package br.com.enap.curso.projeto.repository;

import br.com.enap.curso.projeto.model.Access;
import br.com.enap.curso.projeto.model.TotalByBrowser;
import br.com.enap.curso.projeto.model.TotalByLanguage;
import br.com.enap.curso.projeto.model.TotalByPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access,Long> {


//    @Query("select new br.com.enap.curso.projeto.model.TotalByBrowser(browser, count(b) ) from Population p group by p.country, p.state")
//    public List<TotalByBrowser> getCountByTotalBrowser();


    @Query("select count(a) from Access a where a.shortedLink.alias = :alias")
    long totalHitsByAlias(@Param("alias") String alias);

    @Query("select new br.com.enap.curso.projeto.model.TotalByBrowser(a.browser, count(a)) from Access a inner join a.shortedLink s where a.shortedLink.alias = :alias and a.language is not null group by a.browser")
    List<TotalByBrowser> totalHitsByBrowserByAlias(@Param("alias") String alias);


    @Query("select new br.com.enap.curso.projeto.model.TotalByLanguage(a.language, count(a)) from Access a inner join a.shortedLink s where a.shortedLink.alias = :alias and a.language is not null group by a.language")
    List<TotalByLanguage> totalHitsByLanguageByAlias(@Param("alias") String alias);

    @Query("select new br.com.enap.curso.projeto.model.TotalByPlatform(a.platform, count(a)) from Access a inner join a.shortedLink s where a.shortedLink.alias = :alias and a.language is not null group by a.platform")
    List<TotalByPlatform> totalHitsByPlatformByAlias(@Param("alias") String alias);

}
