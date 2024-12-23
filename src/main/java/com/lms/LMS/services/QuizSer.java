package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Course;
import com.lms.LMS.models.Quiz;
import com.lms.LMS.repositories.CourseRepo;
import com.lms.LMS.repositories.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizSer
{
    private final QuizRepo quizRepo;
    private final CourseRepo courseRepo;

    public Quiz createQuiz(Long courseId, Quiz quiz)
    {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NotFound("Course not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!course.getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to add a quiz to this course");
        }

        quiz.setCourse(course);
        return quizRepo.save(quiz);
    }

    public List<Quiz> getQuizzesByCourse(Long courseId)
    {
        return quizRepo.findByCourseId(courseId);
    }

    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz)
    {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new NotFound("Quiz not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!quiz.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to update this quiz");
        }

        quiz.setTitle(updatedQuiz.getTitle());
        quiz.setDescription(updatedQuiz.getDescription());
        quiz.setQuizDate(updatedQuiz.getQuizDate());
        quiz.setDuration(updatedQuiz.getDuration());
        return quizRepo.save(quiz);
    }

    public void deleteQuiz(Long quizId)
    {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new NotFound("Quiz not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!quiz.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to delete this quiz");
        }

        quizRepo.delete(quiz);
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
