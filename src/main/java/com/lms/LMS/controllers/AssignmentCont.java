package com.lms.LMS.controllers;

import com.lms.LMS.models.Assignment;
import com.lms.LMS.services.AssignmentSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/courses/{courseId}/assignments")
@RestController
public class AssignmentCont
{
    private final AssignmentSer assignmentSer;

    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@PathVariable Long courseId, @RequestBody Assignment assignment)
    {
        try
        {
            Assignment savedAssignment = assignmentSer.createAssignment(courseId, assignment);
            return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAssignmentsByCourse(@PathVariable Long courseId)
    {
        try
        {
            List<Assignment> assignments = assignmentSer.getAssignmentsByCourse(courseId);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId, @RequestBody Assignment updatedAssignment)
    {
        try
        {
            Assignment updated = assignmentSer.updateAssignment(assignmentId, updatedAssignment);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long courseId, @PathVariable Long assignmentId)
    {
        try
        {
            assignmentSer.deleteAssignment(assignmentId);
            return ResponseEntity.ok("Assignment deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
