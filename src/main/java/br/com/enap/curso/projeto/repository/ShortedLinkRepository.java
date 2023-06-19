package br.com.enap.curso.projeto.repository;

import br.com.enap.curso.projeto.model.ShortedLink;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortedLinkRepository extends JpaRepository<ShortedLink, Long> {

    ShortedLink findById(long id);
    ShortedLink findByAlias(String alias);

    @Transactional
    @Modifying
    void deleteByAlias(String alias);

}
