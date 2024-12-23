package com.lms.LMS.services;

import com.lms.LMS.models.Quiz;
import com.lms.LMS.models.QuizSubmission;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.QuizRepo;
import com.lms.LMS.repositories.QuizSubmissionRepo;
import com.lms.LMS.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizSubmissionSer
{
    private final QuizSubmissionRepo quizSubmissionRepo;
    private final QuizRepo quizRepo;
    private final UserRepo userRepo;

    public QuizSubmission submitQuiz(Long quizId, String studentId, List<String> answers)
    {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        User student = userRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        QuizSubmission submission = new QuizSubmission();
        submission.setQuiz(quiz);
        submission.setStudent(student);
        submission.setAnswers(answers);

        return quizSubmissionRepo.save(submission);
    }

    public QuizSubmission gradeQuiz(Long submissionId, Integer grade, String feedback)
    {
        QuizSubmission submission = quizSubmissionRepo.findById(submissionId).orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        return quizSubmissionRepo.save(submission);
    }

    public List<QuizSubmission> getQuizSubmissionsByStudent(String studentId) {
        return quizSubmissionRepo.findByStudentId(studentId);
    }
}