package com.secsystem.emr.shared.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "otp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String otpCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true) // Allow null for dateUpdated
    private Date dateUpdated;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean otpUsed;

    // Method to set dateCreated and dateUpdated
    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Date();
        this.otpUsed = false; // Default value for otpUsed
    }

    // Method to update dateUpdated
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = new Date();
    }
}
