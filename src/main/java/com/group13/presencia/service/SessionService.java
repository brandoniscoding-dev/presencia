package com.group13.presencia.service;

import com.group13.presencia.entity.Session;
import com.group13.presencia.entity.Teacher;
import com.group13.presencia.exception.ResourceNotFoundException;
import com.group13.presencia.repository.SessionRepository;
import com.group13.presencia.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TeacherRepository teacherRepository;

    public SessionService(SessionRepository sessionRepository, TeacherRepository teacherRepository) {
        this.sessionRepository = sessionRepository;
        this.teacherRepository = teacherRepository;
    }

    // Créer une session
    public Session createSession(Long teacherId, double latitude, double longitude) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException ("Enseignant non trouvé avec l'ID : " + teacherId));

        Session session = new Session();
        session.setCode(UUID.randomUUID().toString().substring(0, 6)); // Générer un code aléatoire à 6 caractères
        session.setStartTime(LocalDateTime.now());
        session.setLatitude(latitude);
        session.setLongitude(longitude);
        session.setTeacher(teacher);

        return sessionRepository.save(session);
    }

    // Récupérer une session par son code
    public Session getSessionByCode(String code) {
        return sessionRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec le code : " + code));
    }

    // Récupérer toutes les sessions d'un enseignant
    public List<Session> getSessionsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID : " + teacherId));

        return sessionRepository.findByTeacher(teacher);
    }
}
