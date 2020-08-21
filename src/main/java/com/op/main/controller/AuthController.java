package com.op.main.controller;

import com.op.main.exception.AppException;
import com.op.main.model.Role;
import com.op.main.model.RoleName;
import com.op.main.model.User;
import com.op.main.payload.ApiResponse;
import com.op.main.payload.JwtAuthenticationResponse;
import com.op.main.payload.LoginRequest;
import com.op.main.payload.SignUpRequest;
import com.op.main.repository.RoleRepository;
import com.op.main.repository.UserRepository;
import com.op.main.security.JwtTokenProvider;
import com.op.main.service.VerificationCodeEmailerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	VerificationCodeEmailerService emailService;
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    UUID uid;
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	Optional<User> userdetail =userRepository.findByEmail(signUpRequest.getEmail());
    	
    	if(userdetail.isPresent()==true) {
    		 uid = userdetail.get().getUserId();
    	}
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
    
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!",uid),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!",uid),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        UUID uuid = UUID.randomUUID();
        System.out.println("Random UUID :" + uuid.toString());
        System.out.println("UUID version :" + uuid.version()); 
        user.setUserId(uuid);
       // String userid = user.setUserId(uuid);
        User result = userRepository.save(user);
        emailService.sendSimpleMessage(result);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully",result.getUserId()));
    }
}
