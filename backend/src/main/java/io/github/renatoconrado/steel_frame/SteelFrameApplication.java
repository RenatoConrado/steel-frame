package io.github.renatoconrado.steel_frame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SteelFrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteelFrameApplication.class, args);
    }

}
