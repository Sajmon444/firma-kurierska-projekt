package pl.edu.atar.firmakurierskaprojekt.userregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import pl.edu.atar.firmakurierskaprojekt.userregistration.service.UserRegistrationService;

import java.nio.charset.StandardCharsets;

/**
 * Obsługuje żądania HTTP związane z procesem rejestracji użytkownika.
 *
 * @author Szymon Pawełczyk
 */
@Controller
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    /**
     * Przekierowuje żądanie ze ścieżki głównej na stronę logowania.
     *
     * @return adres przekierowania do widoku logowania
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/rejestracja/logowanie.html";
    }

    /**
     * Przetwarza formularz rejestracji nowego użytkownika i przekierowuje na odpowiedni widok.
     *
     * @param login           login podany w formularzu
     * @param phone           numer telefonu podany w formularzu
     * @param password        hasło podane w formularzu
     * @param confirmPassword powtórzone hasło podane w formularzu
     * @return adres przekierowania do widoku sukcesu lub z powrotem do formularza z komunikatem błędu
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String login,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ) {
        try {
            userRegistrationService.registerUser(login, phone, password, confirmPassword);
            String encodedLogin = UriUtils.encode(login.trim(), StandardCharsets.UTF_8);
            return "redirect:/home.html?login=" + encodedLogin;
        } catch (IllegalArgumentException e) {
            String encodedError = UriUtils.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/rejestracja/rejestracja.html?error=" + encodedError;
        }
    }
}
