package com.group13.presencia.service;

import com.group13.presencia.entity.Teacher;
import com.group13.presencia.exception.ResourceNotFoundException;
import com.group13.presencia.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Enseignant non trouvé avec l'ID : " + id));
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher teacher = getTeacherById(id);
        teacher.setName(updatedTeacher.getName());
        teacher.setEmail(updatedTeacher.getEmail());
        teacher.setPassword(updatedTeacher.getPassword()); // Note : Le mot de passe doit être haché avant la mise à jour
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = getTeacherById(id);
        teacherRepository.delete(teacher);
    }
}
