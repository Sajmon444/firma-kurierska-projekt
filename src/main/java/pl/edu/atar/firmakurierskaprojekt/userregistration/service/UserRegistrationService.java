package pl.edu.atar.firmakurierskaprojekt.userregistration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.User;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.UserRepository;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

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
