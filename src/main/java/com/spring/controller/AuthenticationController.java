package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dao.UserDAO;
import com.spring.entity.Role;
import com.spring.entity.User;
import com.spring.payload.AuthenticationRequest;
import com.spring.payload.JwtResponse;
import com.spring.payload.MessageResponse;
import com.spring.security.jwt.JwtUtils;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	PasswordEncoder encoder;
	
    @Autowired
    JwtUtils jwtUtils;
    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
    	
    	try {
    		Authentication authentication = authenticationManager
    	            .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    	        
    	        SecurityContextHolder.getContext().setAuthentication(authentication);
    	        
    	        JwtResponse jwtResponse = jwtUtils.generateJwt(authentication);
    	        
    	        return ResponseEntity.ok(jwtResponse);
    	}
    	catch (Exception e) {
			throw new RuntimeException("Username or password incorrect!");
		}       
        
    }
    
    @PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
    	
    	if (userDao.existsByUsername(authenticationRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already taken!"));
		}
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_ADMIN"));
		
		User user = new User(authenticationRequest.getUsername(), encoder.encode(authenticationRequest.getPassword()), roles);
		
		userDao.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
    
}
