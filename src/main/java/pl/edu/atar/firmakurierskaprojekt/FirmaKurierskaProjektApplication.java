package pl.edu.atar.firmakurierskaprojekt;

import io.camunda.client.annotation.Deployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa uruchomieniowa aplikacji systemu firmy kurierskiej.
 *
 * @author Szymon Pawełczyk
 */
@SpringBootApplication
@Deployment(resources = "classpath:bpmn/doreczenie.bpmn")
public class FirmaKurierskaProjektApplication {

    /**
     * Uruchamia aplikację Spring Boot.
     *
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {
        SpringApplication.run(FirmaKurierskaProjektApplication.class, args);
    }

}
