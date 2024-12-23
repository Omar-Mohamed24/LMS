package com.lms.LMS.repositories;

import com.lms.LMS.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long>
{
    List<Quiz> findByCourseId(Long courseId);
}
