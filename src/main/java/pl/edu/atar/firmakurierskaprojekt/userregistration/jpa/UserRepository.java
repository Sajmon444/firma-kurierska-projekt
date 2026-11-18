package pl.edu.atar.firmakurierskaprojekt.userregistration.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Zapewnia dostęp do operacji bazodanowych na encji {@link User}.
 *
 * @author Szymon Pawełczyk
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukuje użytkownika na podstawie podanego loginu.
     *
     * @param login login użytkownika
     * @return obiekt {@link Optional} zawierający użytkownika, jeśli został znaleziony
     */
    Optional<User> findByLogin(String login);

    /**
     * Sprawdza, czy w bazie danych istnieje użytkownik o podanym loginie.
     *
     * @param login login użytkownika
     * @return {@code true}, jeśli użytkownik o takim loginie już istnieje, w przeciwnym razie {@code false}
     */
    boolean existsByLogin(String login);
}
