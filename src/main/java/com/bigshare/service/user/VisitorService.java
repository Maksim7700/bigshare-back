package com.bigshare.service.user;

import com.bigshare.enums.Country;
import com.bigshare.model.visitor.Visitor;
import com.bigshare.repository.VisitorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public void checkClientCountry(String address, String countryCode) {
        LocalDate currentDay = LocalDate.now();
        Visitor visitor = visitorRepository.findByAddressAndDate(address, currentDay);
        Country country = Country.valueOf(countryCode);
        Visitor newVisitor = Visitor.builder().address(address).date(LocalDate.now()).countryCode(country.getCountryCode()).country(country.getCountry()).build();
        if (visitor == null) {
            visitorRepository.save(newVisitor);
        } else {
            LocalDate dayOfVisit = visitor.getDate();
            if (dayOfVisit.isBefore(currentDay)) {
                visitorRepository.save(newVisitor);
            }
        }
    }
}
