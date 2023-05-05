package com.example.demo.students;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository)
    {
        return args -> {
            Student sultan = new Student(
                    1L,
                    "Sultan",
                    "Kurmanbek",
                    "ghghghghg@mail.ru",
                    "sfsdsfs",
                    LocalDate.of(2003, Month.JANUARY, 28)
            );
            Student kamila = new Student(
                    2L,
                    "Kamila",
                    "Balakshieva",
                    "erererer@mail.ru",
                    "sfsdsfs",
                    LocalDate.of(2002, Month.SEPTEMBER, 8)
            );
            repository.saveAll(
                    List.of(sultan,kamila)
            );
        };
    }
}
