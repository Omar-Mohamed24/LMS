package com.lms.LMS.controllers;

import com.lms.LMS.models.AssignmentSubmission;
import com.lms.LMS.services.AssignmentSubmissionSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/courses/assignments/submissions")
@RequiredArgsConstructor
public class AssignmentSubmissionCont
{
    private final AssignmentSubmissionSer assignmentSubmissionSer;

    @PostMapping ("/{courseId}/{assignmentId}")
    public ResponseEntity<?> submitAssignment(@PathVariable Long assignmentId, @RequestParam("file") MultipartFile file)
    {
        String studentId = SecurityContextHolder.getContext().getAuthentication().getName();

        String filePath = saveFile(file);

        AssignmentSubmission submission = assignmentSubmissionSer.submitAssignment(assignmentId, studentId, filePath);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @PutMapping("/grade/{submissionId}")
    public ResponseEntity<?> gradeAssignment(@PathVariable Long submissionId, @RequestParam Integer grade, @RequestParam String feedback)
    {
        AssignmentSubmission submission = assignmentSubmissionSer.gradeAssignment(submissionId, grade, feedback);
        return new ResponseEntity<>(submission, HttpStatus.OK);
    }

    private String saveFile(MultipartFile file)
    {
        // Logic to save file to the server or cloud storage
        return "/path/to/saved/file";
    }
}