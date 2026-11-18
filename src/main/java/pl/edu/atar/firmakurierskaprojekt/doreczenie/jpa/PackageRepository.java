package pl.edu.atar.firmakurierskaprojekt.doreczenie.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Zapewnia dostęp do operacji bazodanowych na encji {@link PackageEntity}.
 *
 * @author Szymon Pawełczyk
 */
public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

    /**
     * Pobiera listę paczek nadanych przez wskazanego użytkownika, posortowaną od najnowszych.
     *
     * @param senderLogin login nadawcy paczki
     * @return lista paczek nadanych przez użytkownika
     */
    List<PackageEntity> findBySenderLoginOrderByIdDesc(String senderLogin);

    /**
     * Pobiera listę paczek przypisanych do podanego numeru telefonu odbiorcy, posortowaną od najnowszych.
     *
     * @param recipientPhone numer telefonu odbiorcy
     * @return lista paczek oczekujących dla danego numeru telefonu
     */
    List<PackageEntity> findByRecipientPhoneOrderByIdDesc(String recipientPhone);
}
