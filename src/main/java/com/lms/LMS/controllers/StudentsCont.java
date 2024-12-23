package com.lms.LMS.controllers;

import com.lms.LMS.models.AssignmentSubmission;
import com.lms.LMS.models.Course;
import com.lms.LMS.models.Enrollment;
import com.lms.LMS.models.QuizSubmission;
import com.lms.LMS.services.AssignmentSubmissionSer;
import com.lms.LMS.services.QuizSubmissionSer;
import com.lms.LMS.services.StudentSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentsCont
{
    private final StudentSer studentService;
    private final QuizSubmissionSer quizSubmissionSer;
    private final AssignmentSubmissionSer assignmentSubmissionSer;

    // Get all available courses for a student
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses()
    {
        List<Course> courses = studentService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Get courses by instructor ID
    @GetMapping("/courses/instructor/{instructorId}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable String instructorId)
    {
        List<Course> courses = studentService.getCoursesByInstructor(instructorId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<Enrollment> enrollStudent(@PathVariable Long courseId)
    {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();
        Enrollment enrollment = studentService.enrollStudent(studentId, courseId);
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    @GetMapping("/enrollments")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent()
    {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Enrollment> enrollments = studentService.getEnrollmentsByStudent(studentId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @GetMapping("/submissions/quizzes")
    public ResponseEntity<List<QuizSubmission>> getQuizSubmissions()
    {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<QuizSubmission> submissions = quizSubmissionSer.getQuizSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/submissions/assignments")
    public ResponseEntity<List<AssignmentSubmission>> getAssignmentSubmissions() {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AssignmentSubmission> submissions = assignmentSubmissionSer.getAssignmentSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }
}