package pl.edu.atar.firmakurierskaprojekt.userlogin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import pl.edu.atar.firmakurierskaprojekt.userlogin.service.UserLoginService;

import java.nio.charset.StandardCharsets;

/**
 * Obsługuje żądania HTTP związane z procesem logowania użytkownika.
 *
 * @author Szymon Pawełczyk
 */
@Controller
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userLoginService;

    /**
     * Przetwarza formularz logowania użytkownika i przekierowuje na odpowiedni widok.
     *
     * @param login    login podany w formularzu
     * @param password hasło podane w formularzu
     * @return adres przekierowania do widoku sukcesu lub z powrotem do formularza z komunikatem błędu
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String login,
            @RequestParam String password
    ) {
        try {
            userLoginService.loginUser(login, password);
            return "redirect:/rejestracja/sukces.html";
        } catch (IllegalArgumentException e) {
            String encodedError = UriUtils.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/rejestracja/logowanie.html?error=" + encodedError;
        }
    }
}
