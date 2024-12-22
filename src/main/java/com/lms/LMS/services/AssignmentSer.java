package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Assignment;
import com.lms.LMS.models.Course;
import com.lms.LMS.repositories.AssignmentRepo;
import com.lms.LMS.repositories.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentSer
{
    private final AssignmentRepo assignmentRepo;
    private final CourseRepo courseRepo;

    public Assignment createAssignment(Long courseId, Assignment assignment) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new NotFound("Course not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!course.getInstructor().getId().equals(instructorId) && !isAdmin(instructorId)) {
            throw new RuntimeException("You are not authorized to add an assignment to this course");
        }

        assignment.setCourse(course);
        return assignmentRepo.save(assignment);
    }

    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        return assignmentRepo.findByCourseId(courseId);
    }

    public Assignment updateAssignment(Long assignmentId, Assignment updatedAssignment) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NotFound("Assignment not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!assignment.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId)) {
            throw new RuntimeException("You are not authorized to update this assignment");
        }

        assignment.setTitle(updatedAssignment.getTitle());
        assignment.setDescription(updatedAssignment.getDescription());
        assignment.setDueDate(updatedAssignment.getDueDate());
        return assignmentRepo.save(assignment);
    }

    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NotFound("Assignment not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!assignment.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId)) {
            throw new RuntimeException("You are not authorized to delete this assignment");
        }

        assignmentRepo.delete(assignment);
    }

    // Helpers
    private String getAuthenticatedInstructorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        throw new RuntimeException("No authenticated user found");
    }

    private boolean isAdmin(String instructorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NotFound("No authenticated user found");
        }
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
