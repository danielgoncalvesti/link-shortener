package br.com.enap.curso.projeto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessStatistics {
    private Long totalHits;
    private List<TotalByBrowser> totalByBrowsers;
    private List<TotalByLanguage> totalByLanguages;
    private List<TotalByPlatform> totalByPlatforms;
}
