package com.lms.LMS.controllers;

import com.lms.LMS.models.Enrollment;
import com.lms.LMS.services.TeacherSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherCont
{
    private final TeacherSer teacherService;

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Enrollment>> getEnrolledStudents(@PathVariable Long courseId)
    {
        List<Enrollment> enrollments = teacherService.getEnrolledStudents(courseId);
        return ResponseEntity.ok(enrollments);
    }


    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable Long courseId, @PathVariable String studentId)
    {
        teacherService.removeStudentFromCourse(studentId, courseId);
        return ResponseEntity.ok("Student deleted successfully");
    }
}