package pl.edu.atar.firmakurierskaprojekt.doreczenie.service;

import io.camunda.client.CamundaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.atar.firmakurierskaprojekt.doreczenie.jpa.PackageEntity;
import pl.edu.atar.firmakurierskaprojekt.doreczenie.jpa.PackageRepository;
import pl.edu.atar.firmakurierskaprojekt.userregistration.jpa.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Realizuje logikę biznesową rozpoczynania procesu doręczenia przesyłki w silniku Camunda 8
 * oraz zarządzania zapisanymi paczkami.
 *
 * @author Szymon Pawełczyk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PackageDispatchService {

    private static final String PROCESS_ID = "doreczenie_przesylki";

    private final CamundaClient camundaClient;
    private final PackageRepository packageRepository;
    private final UserRepository userRepository;

    /**
     * Uruchamia nową instancję procesu doręczenia przesyłki, zapisuje paczkę w bazie danych
     * oraz rejestruje informację o starcie procesu w logach serwera.
     *
     * @param senderLogin    login zalogowanego nadawcy
     * @param recipientName  imię i nazwisko odbiorcy
     * @param recipientPhone numer telefonu odbiorcy
     * @param recipientEmail adres e-mail odbiorcy
     * @param deliveryType   wybrany sposób dostawy (HOME lub LOCKER)
     * @param street         ulica i numer domu przy dostawie pod adres
     * @param postalCode     kod pocztowy przy dostawie pod adres
     * @param city           miejscowość przy dostawie pod adres
     * @param lockerCode     kod paczkomatu przy dostawie do paczkomatu
     * @param lockerCity     miejscowość paczkomatu przy dostawie do paczkomatu
     * @param packageSize    gabaryt paczki
     * @param weight         waga paczki w kilogramach
     * @param notes          dodatkowe uwagi do przesyłki
     * @return identyfikator uruchomionej instancji procesu w Camundzie
     */
    public long startDeliveryProcess(
            String senderLogin,
            String recipientName,
            String recipientPhone,
            String recipientEmail,
            String deliveryType,
            String street,
            String postalCode,
            String city,
            String lockerCode,
            String lockerCity,
            String packageSize,
            double weight,
            String notes
    ) {
        String normalizedSender = senderLogin != null ? senderLogin.trim() : "";

        Map<String, Object> variables = new HashMap<>();
        variables.put("senderLogin", normalizedSender);
        variables.put("recipientName", recipientName);
        variables.put("recipientPhone", recipientPhone);
        variables.put("recipientEmail", recipientEmail != null ? recipientEmail : "");
        variables.put("deliveryType", deliveryType);
        variables.put("street", street != null ? street : "");
        variables.put("postalCode", postalCode != null ? postalCode : "");
        variables.put("city", city != null ? city : "");
        variables.put("lockerCode", lockerCode != null ? lockerCode : "");
        variables.put("lockerCity", lockerCity != null ? lockerCity : "");
        variables.put("packageSize", packageSize);
        variables.put("weight", weight);
        variables.put("notes", notes != null ? notes : "");

        var processInstance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId(PROCESS_ID)
                .latestVersion()
                .variables(variables)
                .send()
                .join();

        long instanceKey = processInstance.getProcessInstanceKey();
        log.info("Rozpoczęto proces doręczenia paczki (ID instancji Camunda: {}).", instanceKey);

        PackageEntity packageEntity = PackageEntity.builder()
                .senderLogin(normalizedSender)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientEmail(recipientEmail)
                .deliveryType(deliveryType)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .lockerCode(lockerCode)
                .lockerCity(lockerCity)
                .packageSize(packageSize)
                .weight(weight)
                .notes(notes)
                .processInstanceKey(instanceKey)
                .status("W trakcie realizacji")
                .build();

        packageRepository.save(packageEntity);

        return instanceKey;
    }

    /**
     * Zwraca listę paczek nadanych przez wskazanego użytkownika.
     *
     * @param login login użytkownika
     * @return lista paczek nadanych przez użytkownika
     */
    public List<PackageEntity> getSentPackages(String login) {
        if (login == null || login.isBlank()) {
            return Collections.emptyList();
        }
        return packageRepository.findBySenderLoginOrderByIdDesc(login.trim());
    }

    /**
     * Zwraca listę paczek oczekujących na odbiór przez wskazanego użytkownika
     * na podstawie jego numeru telefonu.
     *
     * @param login login użytkownika
     * @return lista paczek oczekujących
     */
    public List<PackageEntity> getExpectedPackages(String login) {
        if (login == null || login.isBlank()) {
            return Collections.emptyList();
        }
        return userRepository.findByLogin(login.trim())
                .map(user -> packageRepository.findByRecipientPhoneOrderByIdDesc(user.getPhone()))
                .orElse(Collections.emptyList());
    }
}
