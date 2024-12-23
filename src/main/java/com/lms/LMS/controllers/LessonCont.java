package com.lms.LMS.controllers;

import com.lms.LMS.models.Lesson;
import com.lms.LMS.services.LessonSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/lessons")
@Controller
public class LessonCont
{
    private final LessonSer lessonSer;

    @PostMapping("/create")
    public ResponseEntity<?> createLesson(@PathVariable Long courseId, @RequestBody Lesson lesson)
    {
        try
        {
            Lesson savedLesson = lessonSer.createLesson(courseId, lesson);
            return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getLessonsByCourse(@PathVariable Long courseId)
    {
        try
        {
            List<Lesson> lessons = lessonSer.getLessonsByCourse(courseId);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable Long courseId, @PathVariable Long lessonId, @RequestBody Lesson updatedLesson)
    {
        try
        {
            Lesson updated = lessonSer.updateLesson(lessonId, updatedLesson);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable Long courseId, @PathVariable Long lessonId)
    {
        try
        {
            lessonSer.deleteLesson(lessonId);
            return ResponseEntity.ok("Lesson deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
