package com.spring.controller;

import com.spring.model.JwtResponse;
import com.spring.service.CrmServiceClient;
import feign.FeignException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.model.SignupRequest;
import com.spring.model.LoginRequest;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final HttpSession session;

    private final CrmServiceClient crmServiceClient;

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "authentication/login";
        }

        JwtResponse jwt;
        try {
            jwt = crmServiceClient.signIn(loginRequest).getBody();
        } catch (FeignException e) {

            model.addAttribute("message", "Username or password is incorrect!");

            return "authentication/login";
        }

        session.setMaxInactiveInterval(3600); //60 minutes
        session.setAttribute("token", jwt.getToken());
        session.setAttribute("username", jwt.getUsername());
        session.setAttribute("expiryDate", jwt.getExpiryDate());

        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupRequest") SignupRequest signupRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "authentication/register";
        }

        try {
            crmServiceClient.signUp(signupRequest);
        } catch (FeignException e) {

            model.addAttribute("message", "This username is already taken!");

            return "authentication/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}
