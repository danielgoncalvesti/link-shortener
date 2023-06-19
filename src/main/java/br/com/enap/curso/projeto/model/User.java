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
@Table(name= "CUSTOM_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "encoded_pass")
    private String encodedPass;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "apikey")
    private String apikey;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdTime;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedTime;

    public User(String username, String encodedPass, String firstName, String lastName){
        this.username = username;
        this.encodedPass = encodedPass;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String encodedPass, String firstName, String lastName, String apikey){
        this.username = username;
        this.encodedPass = encodedPass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.apikey = apikey;
    }

}
