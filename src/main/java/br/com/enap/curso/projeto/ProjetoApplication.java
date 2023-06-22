package br.com.enap.curso.projeto;

import br.com.enap.curso.projeto.model.ShortedLink;
import br.com.enap.curso.projeto.model.User;
import br.com.enap.curso.projeto.repository.ShortedLinkRepository;
import br.com.enap.curso.projeto.service.ShortedLinkService;
import br.com.enap.curso.projeto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class ProjetoApplication {

	@Autowired
	private ShortedLinkService shortedLinkService;

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ShortedLinkRepository repo) {
		User admin= new User("admin", "321", "Admin", "", "123456");
		userService.saveUser(admin);
		User user1 = new User("daniel", "123", "Daniel", "");
		userService.saveUser(user1);

		return (args) -> {
			shortedLinkService.saveShortedLink(new ShortedLink("https://nti.ufabc.edu.br", "nti-ufabc", false, user1));
			shortedLinkService.saveShortedLink(new ShortedLink("https://google.com.br", "google-br", false, user1));
			shortedLinkService.saveShortedLink(new ShortedLink("https://yahoo.com.br", "yahoo-br", user1));
			shortedLinkService.saveShortedLink(
					new ShortedLink("https://images.unsplash.com/photo-1686902738032-a5d908c2b05b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2127&q=80", "carro-pic", user1));
		};
	}
}
