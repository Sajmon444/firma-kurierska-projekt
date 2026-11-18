package pl.edu.atar.firmakurierskaprojekt.userregistration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.User;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.UserRepository;

/**
 * Realizuje logikę biznesową procesu rejestracji nowego użytkownika.
 *
 * @author Szymon Pawełczyk
 */
@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

    /**
     * Rejestruje nowego użytkownika w systemie po uprzedniej walidacji danych wejściowych.
     *
     * @param login           unikalny login użytkownika
     * @param phone           numer telefonu użytkownika
     * @param password        hasło użytkownika
     * @param confirmPassword powtórzone hasło w celu weryfikacji zgodności
     * @return zapisana encja {@link User}
     * @throws IllegalArgumentException gdy dane są niekompletne, hasła są różne lub login jest już zajęty
     */
    public User registerUser(String login, String phone, String password, String confirmPassword) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login jest wymagany.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Numer telefonu jest wymagany.");
        }
        if (password == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Hasła nie są takie same.");
        }
        if (userRepository.existsByLogin(login.trim())) {
            throw new IllegalArgumentException("Użytkownik o takim loginie już istnieje.");
        }

        User user = User.builder()
                .login(login.trim())
                .phone(phone.trim())
                .password(password)
                .build();

        return userRepository.save(user);
    }
}
