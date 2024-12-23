package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Course;
import com.lms.LMS.models.Enrollment;
import com.lms.LMS.repositories.CourseRepo;
import com.lms.LMS.repositories.EnrollmentRepo;
import com.lms.LMS.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherSer
{
    private final EnrollmentRepo enrollmentRepository;
    private final UserRepo userRepository;
    private final CourseRepo courseRepository;


    public List<Enrollment> getEnrolledStudents(Long courseId)
    {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFound("Course not found"));

        return enrollmentRepository.findByCourseId(courseId);
    }


    public void removeStudentFromCourse(String studentId, Long courseId)
    {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFound("Course not found"));

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new NotFound("Enrollment not found"));

        enrollmentRepository.delete(enrollment);
    }
}
