package com.group13.presencia.service;

import com.group13.presencia.entity.Teacher;

public interface TeacherService {
    Teacher getTeacherById(Long id);
    Teacher updateTeacher(Long id, Teacher updatedTeacher);
    void deleteTeacher(Long id);
}
