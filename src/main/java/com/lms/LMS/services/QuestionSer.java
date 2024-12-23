package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Question;
import com.lms.LMS.models.Quiz;
import com.lms.LMS.repositories.QuestionRepo;
import com.lms.LMS.repositories.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionSer
{
    private final QuestionRepo questionRepo;
    private final QuizRepo quizRepo;

    public Question addQuestion(Long quizId, Question question)
    {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new NotFound("Quiz not found"));
        question.setQuiz(quiz);
        return questionRepo.save(question);
    }

    public List<Question> getQuestionsByQuiz(Long quizId)
    {
        return questionRepo.findByQuizId(quizId);
    }

    public Question updateQuestion(Long questionId, Question updatedQuestion)
    {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));

        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setA(updatedQuestion.getA());
        question.setB(updatedQuestion.getB());
        question.setC(updatedQuestion.getC());
        question.setD(updatedQuestion.getD());
        question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        return questionRepo.save(question);
    }

    public void deleteQuestion(Long questionId)
    {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> new NotFound("Question not found"));
        questionRepo.delete(question);
    }
}
