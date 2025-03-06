package com.group13.presencia.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRequest {
    private Long studentId;
    private String sessionCode;
    private double latitude;
    private double longitude;

    // Getters and Setters
}
