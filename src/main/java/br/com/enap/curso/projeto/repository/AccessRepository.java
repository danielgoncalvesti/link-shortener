package br.com.enap.curso.projeto.repository;

import br.com.enap.curso.projeto.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends JpaRepository<Access,Long> {

}
