package br.com.enap.curso.projeto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AccessStatistics {
    private Integer totalHits;
    private List<TotalByBrowser> totalByBrowsers;

    private List<TotalByLanguage> totalByLanguages;
    private List<TotalByPlatform> totalByPlatforms;
}
