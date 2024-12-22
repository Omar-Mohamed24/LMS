package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Course;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.CourseRepo;
import com.lms.LMS.repositories.UserRepo;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseSer
{
    private final CourseRepo courseRepo;
    private final UserRepo userRepo;


    public Course createCourse(Course course)
    {
        String instructorId = getAuthenticatedInstructorId();
        User instructor = userRepo.findById(instructorId).orElseThrow(() -> new NotFound("Instructor not found"));

        course.setInstructor(instructor);
        return courseRepo.save(course);
    }


    public List<Course> getCoursesByInstructor(String instructorId)
    {
        return courseRepo.findByInstructorId(instructorId);
    }


    public List<Course> getAllCourses()
    {
        return courseRepo.findAll();
    }


    public Course updateCourse(Long courseId, Course updatedCourse)
    {
        String instructorId = getAuthenticatedInstructorId();

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NotFound("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to update this course");
        }

        if (updatedCourse.getTitle() != null && !updatedCourse.getTitle().isEmpty())
        {
            course.setTitle(updatedCourse.getTitle());
        }

        if (updatedCourse.getDescription() != null && !updatedCourse.getDescription().isEmpty())
        {
            course.setDescription(updatedCourse.getDescription());
        }

        if (updatedCourse.getDuration() > 0)
        {
            course.setDuration(updatedCourse.getDuration());
        }
        courseRepo.save(course);
        return course;
    }

    public void deleteCourse(Long courseId)
    {
        String instructorId = getAuthenticatedInstructorId();

        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NotFound("Course not found"));

        if (!course.getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to delete this course");
        }

        courseRepo.delete(course);
    }

    private String getAuthenticatedInstructorId()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            return authentication.getName();
        }
        throw new RuntimeException("No authenticated user found");
    }

    private boolean isAdmin(String instructorId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
        {
            throw new NotFound("No authenticated user found");
        }

        return authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }

}
