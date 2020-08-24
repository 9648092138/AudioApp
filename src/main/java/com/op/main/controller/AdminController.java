package com.op.main.controller;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.op.main.exception.AppException;
import com.op.main.model.Role;
import com.op.main.model.RoleName;
import com.op.main.model.User;
import com.op.main.payload.AdminSignUpRequest;
import com.op.main.payload.ApiResponse;
import com.op.main.payload.JwtAuthenticationResponse;
import com.op.main.payload.LoginRequest;
import com.op.main.payload.SignUpRequest;
import com.op.main.payload.UserDetailRespose;
import com.op.main.repository.RoleRepository;
import com.op.main.repository.UserRepository;
import com.op.main.security.JwtTokenProvider;
import com.op.main.service.VerificationCodeEmailerService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody AdminSignUpRequest signUpRequest) {
    	Optional<User> userdetail =userRepository.findByEmail(signUpRequest.getEmail());
    	Optional<User> usermob =userRepository.findBymobileNo(signUpRequest.getMobileNo());
    	if(userdetail.isPresent()==true) {
    		 uid = userdetail.get().getUserId();
    		 
    	}
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
    
            return new ResponseEntity(new ApiResponse(false, "Username is already Registered!",uid,userdetail.get().getId()),
                    HttpStatus.BAD_REQUEST);
        }
        if(usermob.isPresent()==true) {
            
            return new ResponseEntity(new ApiResponse(false, "this Mobile nuber is already Registered!",usermob.get().getUserId(),usermob.get().getId()),
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!",uid,userdetail.get().getId()),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(),signUpRequest.getMobileNo());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
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

        return ResponseEntity.created(location).body(new ApiResponse(false, "Admin Register successfully",result.getUserId(),result.getId()));
    }
    
  //=================get all registered users in app====================//
  	@GetMapping("/getAllUser")
  	public ResponseEntity<?> getAllusers(){
  		List<User> users =userRepository.findAll();
  		
  		List<UserDetailRespose> response = new ArrayList<UserDetailRespose>();
  		
  		ArrayList<User> 	userlist = new ArrayList<User>();
  		
  		if(users.size()!=0||users.size()>0||users!=null) {
  		for(int i=0;i<users.size();i++) {
  			UserDetailRespose userdetail = new UserDetailRespose();
  			Long id = users.get(i).getId();
//  			Optional<Role> roleName = roleRepository.findById(id);
//  			if(roleName.isPresent()==true) {
//  				userdetail.setRoleName(roleName.get().getName());
//  				response.add(userdetail);
//  			}
  			userdetail.setName(users.get(i).getName());
  			userdetail.setUsername(users.get(i).getUsername());
  			userdetail.setEmail(users.get(i).getEmail());
  			userdetail.setMobileNo(users.get(i).getMobileNo());
  			userdetail.setUserId(users.get(i).getUserId());
  			userdetail.setEnabled(users.get(i).isEnabled());
  			response.add(userdetail);
  		}
  		return ResponseEntity.ok(response);	
  		
  		
  		}else {
  			return ResponseEntity.ok("users not persent");
  		}
		
  		
  	}
  	
}
