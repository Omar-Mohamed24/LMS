package com.lms.LMS.repositories;

import com.lms.LMS.models.Course;
import com.lms.LMS.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Long>
{
    List<Lesson> findByCourseId(Long courseId);
}
