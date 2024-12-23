package com.lms.LMS.controllers;

import com.lms.LMS.models.Question;
import com.lms.LMS.services.QuestionSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/courses/{quizId}/questions")
@Controller
public class QuestionCont
{
    private final QuestionSer questionSer;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@PathVariable Long quizId, @RequestBody Question question)
    {
        try
        {
            Question savedQuestion = questionSer.addQuestion(quizId, question);
            return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getQuestionsByQuiz(@PathVariable Long quizId)
    {
        try
        {
            List<Question> questions = questionSer.getQuestionsByQuiz(quizId);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long quizId, @PathVariable Long questionId, @RequestBody Question updatedQuestion)
    {
        try
        {
            Question updated = questionSer.updateQuestion(questionId, updatedQuestion);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long quizId, @PathVariable Long questionId)
    {
        try
        {
            questionSer.deleteQuestion(questionId);
            return ResponseEntity.ok("Question deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
