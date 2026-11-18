package pl.edu.atar.firmakurierskaprojekt.userregistration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import pl.edu.atar.firmakurierskaprojekt.userregistration.service.UserRegistrationService;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/")
    public String home() {
        return "redirect:/rejestracja/logowanie.html";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String login,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ) {
        try {
            userRegistrationService.registerUser(login, phone, password, confirmPassword);
            return "redirect:/rejestracja/sukces.html";
        } catch (IllegalArgumentException e) {
            String encodedError = UriUtils.encode(e.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/rejestracja/rejestracja.html?error=" + encodedError;
        }
    }
}
