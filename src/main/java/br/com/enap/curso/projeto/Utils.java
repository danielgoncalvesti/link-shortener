package br.com.enap.curso.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utils {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
}
