package pl.edu.atar.firmakurierskaprojekt.userlogin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.User;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.UserRepository;

/**
 * Realizuje logikę biznesową procesu logowania użytkownika.
 *
 * @author Szymon Pawełczyk
 */
@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    /**
     * Weryfikuje dane logowania użytkownika na podstawie loginu i hasła.
     *
     * @param login    login użytkownika
     * @param password hasło użytkownika
     * @return odnaleziona encja {@link User} w przypadku poprawnych danych
     * @throws IllegalArgumentException gdy użytkownik nie istnieje lub podane hasło jest nieprawidłowe
     */
    public User loginUser(String login, String password) {
        if (login == null || login.isBlank() || password == null) {
            throw new IllegalArgumentException("Nieprawidłowy login lub hasło.");
        }

        User user = userRepository.findByLogin(login.trim())
                .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowy login lub hasło."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Nieprawidłowy login lub hasło.");
        }

        return user;
    }
}
