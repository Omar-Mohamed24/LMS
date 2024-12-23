package com.lms.LMS.repositories;

import com.lms.LMS.models.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSubmissionRepo extends JpaRepository<QuizSubmission, Long>
{
    List<QuizSubmission> findByQuiz_Id(Long quizId);
    List<QuizSubmission> findByStudentId(String studentId);
}
