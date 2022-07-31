package com.bigshare.model.visitor;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "visitor")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "date_visit")
    @CreatedDate
    private LocalDate date;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country")
    private String country;

}
