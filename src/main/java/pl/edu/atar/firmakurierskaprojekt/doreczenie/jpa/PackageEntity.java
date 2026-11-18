package pl.edu.atar.firmakurierskaprojekt.doreczenie.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Reprezentuje encję paczki nadanej w procesie doręczenia.
 *
 * @author Szymon Pawełczyk
 */
@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderLogin;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String recipientPhone;

    private String recipientEmail;

    @Column(nullable = false)
    private String deliveryType;

    private String street;

    private String postalCode;

    private String city;

    private String lockerCode;

    private String lockerCity;

    @Column(nullable = false)
    private String packageSize;

    @Column(nullable = false)
    private double weight;

    private String notes;

    private Long processInstanceKey;

    @Column(nullable = false)
    private String status;
}
