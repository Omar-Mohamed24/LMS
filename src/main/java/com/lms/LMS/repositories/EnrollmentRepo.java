package com.lms.LMS.repositories;

import com.lms.LMS.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepo extends JpaRepository<Enrollment, Long>
{
    List<Enrollment> findByStudentId(String studentId);
    boolean existsByStudentIdAndCourseId(String studentId, Long courseId);
}
