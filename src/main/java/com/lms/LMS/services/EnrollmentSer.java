package com.lms.LMS.services;

import com.lms.LMS.models.Course;
import com.lms.LMS.models.Enrollment;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.CourseRepo;
import com.lms.LMS.repositories.EnrollmentRepo;
import com.lms.LMS.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentSer
{
    private EnrollmentRepo enrollmentRepository;
    private UserRepo userRepository;
    private CourseRepo courseRepository;

    public Enrollment enrollStudent(String studentId, Long courseId)
    {
        User student = userRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId))
        {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(String studentId)
    {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
