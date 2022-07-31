package com.bigshare.controller.analisys;

import com.bigshare.service.user.VisitorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/api/user/count")
public class CountUserController {

    private final VisitorService visitorService;

    public CountUserController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    public void countUsers(HttpServletRequest request) {
        String currentCountryCode = request.getLocale().getISO3Country();
        String remoteAddress = request.getRemoteAddr();
        visitorService.checkClientCountry(remoteAddress, currentCountryCode);
    }
}
