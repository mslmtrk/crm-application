package com.spring.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.model.SignupRequest;
import com.spring.model.Customer;
import com.spring.model.LoginRequest;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final HttpSession session;

    private final CrmServiceClient crmServiceClient;

    @GetMapping("/")
    public String showCustomersList(Model model) {

        List<Customer> customers = crmServiceClient.getCustomers().getBody();
        model.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/customer-form")
    public String showCustomerForm(Model model) {

        if (session.getAttribute("token") == null || (new Date()).after((Date) session.getAttribute("expiryDate"))) {
            return "redirect:/login";
        }

        Customer customer = new Customer();
        model.addAttribute("customer", customer);

        return "customer-form";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model) {

        if (session.getAttribute("token") == null || (new Date()).after((Date) session.getAttribute("expiryDate"))) {
            return "redirect:/login";
        } else if (result.hasErrors()) {
            return "customer-form";
        }

        if (customer.getId() == null) {

            try {
                crmServiceClient.saveCustomer(getAuthorizationHeaderValue(), customer);
            } catch (FeignException e) {
                model.addAttribute("message", "This email address has been taken!");
                return "customer-form";
            }
        } else {

            try {
                crmServiceClient.updateCustomer(getAuthorizationHeaderValue(), customer);
            } catch (FeignException e) {
                model.addAttribute("message", "This email address has been taken!");
                return "customer-form";
            }
        }

        return "redirect:/";
    }

    @GetMapping("/update-customer")
    public String showUpdateCustomerForm(@RequestParam(name = "customerId") Long customerId, Model model) {

        if (session.getAttribute("token") == null || (new Date()).after((Date) session.getAttribute("expiryDate"))) {
            return "redirect:/login";
        }

        Customer customer = crmServiceClient.getCustomer(getAuthorizationHeaderValue(), customerId).getBody();
        model.addAttribute("customer", customer);

        return "customer-form";
    }

    @GetMapping("/delete-customer")
    public String deleteCustomer(@RequestParam(name = "customerId") Long customerId) {

        if (session.getAttribute("token") == null || (new Date()).after((Date) session.getAttribute("expiryDate"))) {
            return "redirect:/login";
        }

        crmServiceClient.deleteCustomer(getAuthorizationHeaderValue(), customerId);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {

        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);

        return "authentication/login";
    }

    @GetMapping("/signup")
    public String showRegisterForm(Model model) {

        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signupRequest", signupRequest);

        return "authentication/register";
    }

    private String getAuthorizationHeaderValue() {
        return "Bearer " + session.getAttribute("token");
    }
}
