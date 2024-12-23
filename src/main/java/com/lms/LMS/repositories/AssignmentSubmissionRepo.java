package com.lms.LMS.repositories;

import com.lms.LMS.models.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission, Long>
{
    List<AssignmentSubmission> findByAssignment_Id(Long assignmentId);
    List<AssignmentSubmission> findByStudentId(String studentId);
}
