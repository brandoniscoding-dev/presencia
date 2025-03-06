package com.group13.presencia.service;

import com.group13.presencia.entity.Attendance;
import com.group13.presencia.entity.Session;
import com.group13.presencia.entity.Student;
import com.group13.presencia.exception.ResourceNotFoundException;
import com.group13.presencia.repository.AttendanceRepository;
import com.group13.presencia.repository.SessionRepository;
import com.group13.presencia.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, SessionRepository sessionRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
    }

    // Valider une présence
    public Attendance validateAttendance(Long studentId, String sessionCode, double latitude, double longitude) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + studentId));

        Session session = sessionRepository.findByCode(sessionCode)
                .orElseThrow(() -> new ResourceNotFoundException ("Session non trouvée avec le code : " + sessionCode));

        // Vérifier la localisation GPS
        if (!isWithinRadius(session.getLatitude(), session.getLongitude(), latitude, longitude, 50)) {
            throw new RuntimeException("L'étudiant n'est pas dans la salle de classe");
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setSession(session);
        attendance.setLatitude(latitude);
        attendance.setLongitude(longitude);
        attendance.setTimestamp(LocalDateTime.now());

        return attendanceRepository.save(attendance);
    }

    // Vérifier si l'étudiant est dans un rayon donné
    private boolean isWithinRadius(double sessionLat, double sessionLon, double studentLat, double studentLon, double radiusInMeters) {
        double earthRadius = 6371000; // Rayon de la Terre en mètres
        double dLat = Math.toRadians(studentLat - sessionLat);
        double dLon = Math.toRadians(studentLon - sessionLon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(sessionLat)) * Math.cos(Math.toRadians(studentLat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance <= radiusInMeters;
    }

    // Récupérer les présences d'un étudiant
    public List<Attendance> getAttendancesByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant non trouvé avec l'ID : " + studentId));

        return attendanceRepository.findByStudent(student);
    }

    // Récupérer les présences d'une session
    public List<Attendance> getAttendancesBySession(String sessionCode) {
        Session session = sessionRepository.findByCode(sessionCode)
                .orElseThrow(() -> new ResourceNotFoundException("Session non trouvée avec le code : " + sessionCode));

        return attendanceRepository.findBySession(session);
    }
}
