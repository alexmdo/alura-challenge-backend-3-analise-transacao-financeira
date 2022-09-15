package br.com.alexmdo.txanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
@SpringBootApplication
public class AluraChallengeBackend3AnaliseTransacaoFinanceiraApplication {

    public static void main(String[] args) {
        SpringApplication.run(AluraChallengeBackend3AnaliseTransacaoFinanceiraApplication.class, args);
    }

    @Bean
    public Validator validator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }

}
