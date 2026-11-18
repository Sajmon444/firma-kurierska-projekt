package pl.edu.atar.firmakurierskaprojekt.doreczenie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import pl.edu.atar.firmakurierskaprojekt.doreczenie.jpa.PackageEntity;
import pl.edu.atar.firmakurierskaprojekt.doreczenie.service.PackageDispatchService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Obsługuje żądania HTTP związane z nadawaniem paczki, startowaniem procesu doręczenia
 * oraz pobieraniem list paczek zalogowanego użytkownika.
 *
 * @author Szymon Pawełczyk
 */
@Controller
@RequiredArgsConstructor
public class PackageDispatchController {

    private final PackageDispatchService packageDispatchService;

    /**
     * Odbiera dane z formularza nadania paczki, zapisuje paczkę i uruchamia proces BPMN w Camundzie 8.
     *
     * @param senderLogin    login zalogowanego nadawcy
     * @param recipientName  imię i nazwisko odbiorcy
     * @param recipientPhone numer telefonu odbiorcy
     * @param recipientEmail adres e-mail odbiorcy
     * @param deliveryType   wybrany sposób dostawy (HOME lub LOCKER)
     * @param street         ulica i numer domu
     * @param postalCode     kod pocztowy
     * @param city           miejscowość
     * @param lockerCode     kod paczkomatu
     * @param lockerCity     miejscowość paczkomatu
     * @param packageSize    gabaryt paczki
     * @param weight         waga paczki w kilogramach
     * @param notes          uwagi do przesyłki
     * @return przekierowanie na panel główny zalogowanego użytkownika
     */
    @PostMapping("/doreczenie/nadaj")
    public String dispatchPackage(
            @RequestParam(required = false) String senderLogin,
            @RequestParam String recipientName,
            @RequestParam String recipientPhone,
            @RequestParam(required = false) String recipientEmail,
            @RequestParam String deliveryType,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String postalCode,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String lockerCode,
            @RequestParam(required = false) String lockerCity,
            @RequestParam String packageSize,
            @RequestParam double weight,
            @RequestParam(required = false) String notes
    ) {
        packageDispatchService.startDeliveryProcess(
                senderLogin,
                recipientName,
                recipientPhone,
                recipientEmail,
                deliveryType,
                street,
                postalCode,
                city,
                lockerCode,
                lockerCity,
                packageSize,
                weight,
                notes
        );

        String redirectUrl = "/home.html";
        if (senderLogin != null && !senderLogin.isBlank()) {
            redirectUrl += "?login=" + UriUtils.encode(senderLogin.trim(), StandardCharsets.UTF_8);
        }

        return "redirect:" + redirectUrl;
    }

    /**
     * Zwraca paczki nadane oraz oczekujące dla wskazanego użytkownika w formacie JSON.
     *
     * @param login login zalogowanego użytkownika
     * @return mapa zawierająca listy paczek {@code sent} oraz {@code expected}
     */
    @GetMapping("/api/doreczenie/paczki")
    @ResponseBody
    public Map<String, List<PackageEntity>> getUserPackages(@RequestParam(required = false) String login) {
        return Map.of(
                "sent", packageDispatchService.getSentPackages(login),
                "expected", packageDispatchService.getExpectedPackages(login)
        );
    }
}
