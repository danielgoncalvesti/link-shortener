package br.com.enap.curso.projeto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalByBrowser {

    private String browser;
    private int totalHits;
}
