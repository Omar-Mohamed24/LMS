package com.lms.LMS.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "AssignmentSubmission")
public class AssignmentSubmission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private User student;

    private String filePath; // Path to uploaded file

    private Integer grade;

    private String feedback;
}
