package com.akihito.spring_security.controllers;

import com.akihito.spring_security.dtos.RegisterRecordDTO;
import com.akihito.spring_security.dtos.SignlnRecordDTO;
import com.akihito.spring_security.models.UserModel;
import com.akihito.spring_security.repositories.UserRepository;
import com.akihito.spring_security.services.TokenService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping("signln")
    public ResponseEntity login(@RequestBody @Valid SignlnRecordDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                data.username(),
                data.password()
        );
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterRecordDTO data){
        if(userRepository.findByUsername(data.username()) != null)
            return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var userModel = new UserModel();
        BeanUtils.copyProperties(data, userModel);
        userModel.setPassword(encryptedPassword);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(userRepository.save(userModel));

    }

}
