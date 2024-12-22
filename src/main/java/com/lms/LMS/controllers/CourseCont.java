package com.lms.LMS.controllers;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Course;
import com.lms.LMS.services.CourseSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Controller
public class CourseCont
{
    private final CourseSer courseSer;

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course)
    {
        try
        {
            Course savedCourse = courseSer.createCourse(course);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        }
        catch (NotFound e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<?> getCoursesByInstructor(@PathVariable String instructorId)
    {
        List<Course> courses = courseSer.getCoursesByInstructor(instructorId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses()
    {
        List<Course> courses = courseSer.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse)
    {
        try
        {
            Course updated = courseSer.updateCourse(courseId, updatedCourse);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (NotFound e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId)
    {
        try
        {
            courseSer.deleteCourse(courseId);
            return ResponseEntity.ok("Course deleted successfully");
        }
        catch (NotFound e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
