package br.com.enap.curso.projeto.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="Access")
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "browser")
    private String browser;

    @Column(name="language")
    private String language;

    @Column(name="platform")
    private String platform;

    @Column(name="user_agent")
    private String userAgentData;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdTime;

    @ManyToOne
    private ShortedLink shortedLink;
}
