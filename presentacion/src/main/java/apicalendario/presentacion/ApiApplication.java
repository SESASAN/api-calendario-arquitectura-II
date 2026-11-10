package apicalendario.presentacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "apicalendario")
@EnableJpaRepositories(basePackages = "apicalendario.infraestructura.persistencia.repositorios.interfaces")
@EntityScan(basePackages = "apicalendario.infraestructura.persistencia.entidades")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
