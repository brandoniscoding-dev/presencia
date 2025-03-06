package com.group13.presencia.service;



import com.group13.presencia.entity.Student;
import com.group13.presencia.exception.ResourceNotFoundException;
import com.group13.presencia.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Étudiant non trouvé avec l'ID : " + id));
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setPassword(updatedStudent.getPassword()); // Note : Le mot de passe doit être haché avant la mise à jour
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
