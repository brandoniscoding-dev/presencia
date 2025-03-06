package com.group13.presencia.repository;

import com.group13.presencia.entity.Attendance;
import com.group13.presencia.entity.Session;
import com.group13.presencia.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent(Student student);

    // Récupérer toutes les présences d'une session
    List<Attendance> findBySession(Session session);
}