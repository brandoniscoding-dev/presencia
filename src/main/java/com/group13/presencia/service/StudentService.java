package com.group13.presencia.service;

import com.group13.presencia.entity.Student;

public interface StudentService {
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student updatedStudent);
    void deleteStudent(Long id);
}
