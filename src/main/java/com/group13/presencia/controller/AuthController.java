package com.group13.presencia.controller;

import com.group13.presencia.dto.LoginRequest;
import com.group13.presencia.dto.RegisterRequest;
import com.group13.presencia.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody RegisterRequest request) {
        authService.registerTeacher(request);
        return ResponseEntity.ok("Enseignant inscrit avec succès");
    }

    @PostMapping("/register/student")
    public ResponseEntity<String> registerStudent(@RequestBody RegisterRequest request) {
        authService.registerStudent(request);
        return ResponseEntity.ok("Étudiant inscrit avec succès");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken (request.getUsername(), request.getPassword())
            );
            return ResponseEntity.ok("Connexion réussie pour l'utilisateur : " + authentication.getName());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Échec de la connexion : Identifiants invalides");
        }
}