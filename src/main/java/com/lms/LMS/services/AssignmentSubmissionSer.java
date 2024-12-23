package com.lms.LMS.services;

import com.lms.LMS.models.Assignment;
import com.lms.LMS.models.AssignmentSubmission;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.AssignmentRepo;
import com.lms.LMS.repositories.AssignmentSubmissionRepo;
import com.lms.LMS.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentSubmissionSer
{
    private final AssignmentSubmissionRepo assignmentSubmissionRepo;
    private final AssignmentRepo assignmentRepo;
    private final UserRepo userRepo;

    public AssignmentSubmission submitAssignment(Long assignmentId, String studentId, String filePath)
    {
        Assignment assignment = assignmentRepo.findById(assignmentId).orElseThrow(() -> new RuntimeException("Assignment not found"));
        User student = userRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));

        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setFilePath(filePath);

        return assignmentSubmissionRepo.save(submission);
    }

    public AssignmentSubmission gradeAssignment(Long submissionId, Integer grade, String feedback)
    {
        AssignmentSubmission submission = assignmentSubmissionRepo.findById(submissionId).orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        return assignmentSubmissionRepo.save(submission);
    }

    public List<AssignmentSubmission> getAssignmentSubmissionsByStudent(String studentId)
    {
        return assignmentSubmissionRepo.findByStudentId(studentId);
    }
}
