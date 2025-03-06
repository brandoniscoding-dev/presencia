package com.group13.presencia.controller;

import com.group13.presencia.entity.Attendance;
import com.group13.presencia.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<Attendance> validateAttendance(
            @RequestParam Long studentId,
            @RequestParam String sessionCode,
            @RequestParam double latitude,
            @RequestParam double longitude) {
        Attendance attendance = attendanceService.validateAttendance(studentId, sessionCode, latitude, longitude);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendancesByStudent(@PathVariable Long studentId) {
        List<Attendance> attendances = attendanceService.getAttendancesByStudent(studentId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/session/{sessionCode}")
    public ResponseEntity<List<Attendance>> getAttendancesBySession(@PathVariable String sessionCode) {
        List<Attendance> attendances = attendanceService.getAttendancesBySession(sessionCode);
        return ResponseEntity.ok(attendances);
    }
}