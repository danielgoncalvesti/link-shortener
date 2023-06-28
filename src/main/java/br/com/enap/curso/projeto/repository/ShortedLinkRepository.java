package br.com.enap.curso.projeto.repository;

import br.com.enap.curso.projeto.model.ShortedLink;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortedLinkRepository extends JpaRepository<ShortedLink, Long> {

    ShortedLink findById(long id);
    ShortedLink findByAlias(String alias);

    @Query("SELECT s FROM ShortedLink s WHERE s.user.id = :userId")
    List<ShortedLink> getAllShortedLinkByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    void deleteByAlias(String alias);

}
