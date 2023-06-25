package br.com.enap.curso.projeto.service;

import br.com.enap.curso.projeto.errorHandling.ValidationError;
import br.com.enap.curso.projeto.model.User;
import br.com.enap.curso.projeto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void saveUser(User user){
        String encoderPassword = this.passwordEncoder.encode(user.getEncodedPass());
        user.setEncodedPass(encoderPassword);
        this.userRepository.save(user);
    }

    public User getValidUserCredentials(String username, String pass) {
        User user = this.userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(pass, user.getEncodedPass())){
            return user;
        } else {
            throw new ValidationError("Invalid user credentials", HttpStatus.UNAUTHORIZED.value());
        }

    }
}
