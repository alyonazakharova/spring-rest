package main;

import main.entity.Good;
import main.entity.Sale;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestDataInit implements CommandLineRunner {

    @Autowired
    GoodRepository goodRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    Warehouse1Repository warehouse1Repository;

    @Autowired
    Warehouse2Repository warehouse2Repository;

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
        saleRepository.save(new Sale(good1, 5, LocalDateTime.now()));
        saleRepository.save(new Sale(good2, 55, LocalDateTime.now()));
        warehouse1Repository.save(new Warehouse1(good1, 100));
        warehouse2Repository.save(new Warehouse2(good2, 200));

//        userRepository.save(new User("user", pwdEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
//        userRepository.save(new User("admin", pwdEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
