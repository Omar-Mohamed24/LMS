package com.lms.LMS.controllers;

import com.lms.LMS.models.Quiz;
import com.lms.LMS.services.QuizSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/quizzes")
@RestController
public class QuizCont
{
    private final QuizSer quizSer;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@PathVariable Long courseId, @RequestBody Quiz quiz)
    {
        try
        {
            Quiz savedQuiz = quizSer.createQuiz(courseId, quiz);
            return new ResponseEntity<>(savedQuiz, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getQuizzesByCourse(@PathVariable Long courseId)
    {
        try
        {
            List<Quiz> quizzes = quizSer.getQuizzesByCourse(courseId);
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{quizId}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long courseId, @PathVariable Long quizId, @RequestBody Quiz updatedQuiz)
    {
        try
        {
            Quiz updated = quizSer.updateQuiz(quizId, updatedQuiz);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long courseId, @PathVariable Long quizId)
    {
        try
        {
            quizSer.deleteQuiz(quizId);
            return ResponseEntity.ok("Quiz deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
