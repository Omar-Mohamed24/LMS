package com.lms.LMS.controllers;

import com.lms.LMS.models.QuizSubmission;
import com.lms.LMS.services.QuizSubmissionSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses/quizzes/submissions")
@RequiredArgsConstructor
public class QuizSubmissionCont
{
    private final QuizSubmissionSer quizSubmissionSer;

    @PostMapping("{courseId}/{quizId}")
    public ResponseEntity<?> submitQuiz(@PathVariable Long quizId, @RequestBody List<String> answers)
    {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();
        QuizSubmission submission = quizSubmissionSer.submitQuiz(quizId, studentId, answers);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @PutMapping("/grade/{submissionId}")
    public ResponseEntity<?> gradeQuiz(@PathVariable Long submissionId, @RequestParam Integer grade, @RequestParam String feedback) {
        QuizSubmission submission = quizSubmissionSer.gradeQuiz(submissionId, grade, feedback);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }
}
