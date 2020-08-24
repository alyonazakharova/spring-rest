package main;

import main.entity.Good;
import main.entity.Sale;
import main.entity.Warehouse1;
import main.entity.Warehouse2;
import main.repository.GoodRepository;
import main.repository.SaleRepository;
import main.repository.Warehouse1Repository;
import main.repository.Warehouse2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringRestApp {

    private static final Logger log = LoggerFactory.getLogger(SpringRestApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApp.class, args);
    }

    @Bean
    public CommandLineRunner test(GoodRepository goodRepository, SaleRepository saleRepository,
                                  Warehouse1Repository warehouse1Repository, Warehouse2Repository warehouse2Repository) {
        return args -> {
            Good good1 = new Good("good1", 0.2);
            Good good2 = new Good("good2", 0.04);
            goodRepository.save(good1);
            goodRepository.save(good2);
            saleRepository.save(new Sale(good1, 5, LocalDateTime.now()));
            saleRepository.save(new Sale(good2, 25, LocalDateTime.now()));
            warehouse1Repository.save(new Warehouse1(good1, 100));
            warehouse1Repository.save(new Warehouse1(good1, 55));
            warehouse2Repository.save(new Warehouse2(good1, 666));

            for (Good good : goodRepository.findAll()) {
                log.info("The good is: " + good.toString());
            }

            for (Sale sale : saleRepository.findAll()) {
                log.info("The sale is: " + sale.toString());
            }
        };
    }
}

