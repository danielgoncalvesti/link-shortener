package br.com.enap.curso.projeto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TotalByPlatform {

    private String platform;
    private int totalHits;
}
