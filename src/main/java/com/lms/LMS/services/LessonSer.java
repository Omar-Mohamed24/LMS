package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Course;
import com.lms.LMS.models.Lesson;
import com.lms.LMS.repositories.CourseRepo;
import com.lms.LMS.repositories.LessonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class LessonSer
{
    private final LessonRepo lessonRepo;
    private final CourseRepo courseRepo;

    public Lesson createLesson(Long courseId, Lesson lesson)
    {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NotFound("Course not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!course.getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to add a lesson to this course");
        }

        lesson.setCourse(course);
        return lessonRepo.save(lesson);
    }

    public List<Lesson> getLessonsByCourse(Long courseId)
    {
        return lessonRepo.findByCourseId(courseId);
    }

    public Lesson updateLesson(Long lessonId, Lesson updatedLesson)
    {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new NotFound("Lesson not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!lesson.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to update this lesson");
        }

        lesson.setTitle(updatedLesson.getTitle());
        lesson.setDate(updatedLesson.getDate());
        return lessonRepo.save(lesson);
    }

    public void deleteLesson(Long lessonId)
    {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new NotFound("Lesson not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!lesson.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to delete this lesson");
        }

        lessonRepo.delete(lesson);
    }

    // Helpers
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
