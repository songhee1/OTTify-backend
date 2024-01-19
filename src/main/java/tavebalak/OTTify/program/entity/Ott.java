package tavebalak.OTTify.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ott {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_id")
    private Long id;

    private String name;
    private String logoPath;
    private String subscribeLogoPath;

    private Long tmDbProviderId;
}
