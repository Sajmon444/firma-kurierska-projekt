package pl.edu.atar.firmakurierskaprojekt;

import io.camunda.client.CamundaClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

/**
 * Testuje poprawne ładowanie kontekstu aplikacji Spring Boot.
 *
 * @author Szymon Pawełczyk
 */
@SpringBootTest(properties = "camunda.client.enabled=false")
class FirmaKurierskaProjektApplicationTests {

    @MockitoBean
    private CamundaClient camundaClient;

    @Test
    void contextLoads() {
    }

}
