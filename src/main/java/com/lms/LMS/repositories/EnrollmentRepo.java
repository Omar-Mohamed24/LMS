package com.lms.LMS.repositories;

import com.lms.LMS.models.Course;
import com.lms.LMS.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long>
{
    List<Enrollment> findByStudentId(String studentId);
    boolean existsByStudentIdAndCourseId(String studentId, Long courseId);
    List<Enrollment> findByCourseId(Long courseId);
    Optional<Enrollment> findByStudentIdAndCourseId(String studentId, Long courseId);
}
