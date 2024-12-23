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
public class StudentSer
{
    private final EnrollmentRepo enrollmentRepository;
    private final UserRepo userRepository;
    private final CourseRepo courseRepository;

    public List<Course> getAllCourses()
    {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::excludeEnrollmentsFromCourse)
                .map(this::excludeCorrectAnswersFromQuizzes)
                .toList();
    }

    public List<Course> getCoursesByInstructor(String instructorId)
    {
        List<Course> courses = courseRepository.findByInstructorId(instructorId);
        return courses.stream()
                .map(this::excludeEnrollmentsFromCourse)
                .map(this::excludeCorrectAnswersFromQuizzes)
                .toList();
    }

    // Helper to exclude enrollments
    private Course excludeEnrollmentsFromCourse(Course course)
    {
        Course newCourse = new Course();
        newCourse.setId(course.getId());
        newCourse.setTitle(course.getTitle());
        newCourse.setDescription(course.getDescription());
        newCourse.setInstructor(course.getInstructor());
        newCourse.setAssignments(course.getAssignments());
        newCourse.setQuizzes(course.getQuizzes());
        newCourse.setLessons(course.getLessons());
        newCourse.setEnrollments(null);
        return newCourse;
    }


    private Course excludeCorrectAnswersFromQuizzes(Course course)
    {
        course.getQuizzes().forEach(quiz -> {
            quiz.getQuestions().forEach(question -> {
                question.setCorrectAnswer(null);
            });
        });
        return course;
    }

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
