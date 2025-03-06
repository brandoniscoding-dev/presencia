package com.group13.presencia.service;

import com.group13.presencia.entity.Student;
import com.group13.presencia.entity.Teacher;
import com.group13.presencia.repository.StudentRepository;
import com.group13.presencia.repository.TeacherRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CustomUserDetailsService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Vérifier si l'utilisateur est un enseignant
        Teacher teacher = teacherRepository.findByEmail(email)
                .orElse(null);

        if (teacher != null) {
            return new CustomUserDetails(teacher);
        }

        // Vérifier si l'utilisateur est un étudiant
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return new CustomUserDetails(student);
    }
}
