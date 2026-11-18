package pl.edu.atar.firmakurierskaprojekt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.User;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.UserRepository;

/**
 * Inicjalizuje przykładowe dane w bazie H2 po uruchomieniu aplikacji w profilu deweloperskim.
 *
 * @author Szymon Pawełczyk
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    /**
     * Dodaje domyślnego użytkownika testowego do bazy danych, jeśli jeszcze nie istnieje.
     *
     * @param args argumenty wiersza poleceń aplikacji
     */
    @Override
    public void run(String... args) {
        if (!userRepository.existsByLogin("test")) {
            User testUser = User.builder()
                    .login("test")
                    .phone("123456789")
                    .password("testtest")
                    .build();
            userRepository.save(testUser);
        }
    }
}
