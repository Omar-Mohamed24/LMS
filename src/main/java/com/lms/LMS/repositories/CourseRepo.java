package com.lms.LMS.repositories;

import com.lms.LMS.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long>
{
    List<Course> findByInstructorId(String instructorId);
}
