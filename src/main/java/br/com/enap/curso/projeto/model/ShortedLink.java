package br.com.enap.curso.projeto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ShortedLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name= "alias",unique = true, nullable = false)
    private String alias;

    @Column(name= "is_private", nullable = false)
    private Boolean isprivate;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedTime;

    @ManyToOne
    private User user;

    public ShortedLink(String link, String alias, User user){
        this.link = link;
        this.alias = alias;
        this.isprivate = false;
        this.user = user;
    }

    public ShortedLink(String link, String alias, Boolean isprivate, User user){
        this.link = link;
        this.alias = alias;
        this.isprivate = isprivate;
        this.user = user;
    }
}
