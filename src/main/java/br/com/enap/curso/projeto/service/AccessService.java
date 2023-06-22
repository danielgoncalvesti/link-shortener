package br.com.enap.curso.projeto.service;

import br.com.enap.curso.projeto.model.Access;
import br.com.enap.curso.projeto.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    @Autowired
    private AccessRepository repo;

    public void saveAccess(Access access){
        repo.save(access);
    }
}
