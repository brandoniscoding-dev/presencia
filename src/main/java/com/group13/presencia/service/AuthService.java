package com.group13.presencia.service;

import com.group13.presencia.dto.RegisterRequest;
import com.group13.presencia.entity.Student;
import com.group13.presencia.entity.Teacher;
import com.group13.presencia.repository.StudentRepository;
import com.group13.presencia.repository.TeacherRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TeacherRepository teacherRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerTeacher(RegisterRequest request) {
        if (teacherRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        teacherRepository.save(teacher);
    }

    public void registerStudent(RegisterRequest request) {
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        studentRepository.save(student);
    }
}