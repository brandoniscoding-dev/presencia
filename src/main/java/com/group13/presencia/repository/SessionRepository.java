package com.group13.presencia.repository;

import com.group13.presencia.entity.Session;
import com.group13.presencia.entity.Teacher;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByCode(String code);

    List<Session> findByTeacher(Teacher teacher);

}
