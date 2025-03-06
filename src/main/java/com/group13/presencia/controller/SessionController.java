package com.group13.presencia.controller;

import com.group13.presencia.entity.Session;
import com.group13.presencia.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<Session> createSession(
            @RequestParam Long teacherId,
            @RequestParam double latitude,
            @RequestParam double longitude) {
        Session session = sessionService.createSession(teacherId, latitude, longitude);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Session> getSessionByCode(@PathVariable String code) {
        Session session = sessionService.getSessionByCode(code);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Session>> getSessionsByTeacher(@PathVariable Long teacherId) {
        List<Session> sessions = sessionService.getSessionsByTeacher(teacherId);
        return ResponseEntity.ok(sessions);
    }
}