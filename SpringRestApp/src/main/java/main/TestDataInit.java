package main;

import main.entity.Good;
import main.entity.User;
import main.repository.GoodRepository;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    GoodRepository goodRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {
        Good good1 = new Good("good1", 0.2);
        Good good2 = new Good("good2", 0.04);
        goodRepository.save(good1);
        goodRepository.save(good2);

        userRepository.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
