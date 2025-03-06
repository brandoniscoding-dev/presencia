package com.group13.presencia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionRequest {
    private Long teacherId;
    private double latitude;
    private double longitude;

    // Getters and Setters
}
