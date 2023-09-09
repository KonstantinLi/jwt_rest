package com.kostyali.security_jwt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/unsecured")
    public String unsecured() {
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Secured data";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin data";
    }

    @GetMapping("/info")
    public String userInfo(Principal principal) {
        return principal.getName();
    }
}
