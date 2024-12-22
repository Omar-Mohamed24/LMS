package com.lms.LMS.services;

import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Lesson;
import com.lms.LMS.models.Media;
import com.lms.LMS.repositories.LessonRepo;
import com.lms.LMS.repositories.MediaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MediaSer
{
    private final MediaRepo mediaRepo;
    private final LessonRepo lessonRepo;

    public Media addMediaToLesson(Long lessonId, Media media)
    {
        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() -> new NotFound("Lesson not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!lesson.getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to add media to this lesson");
        }

        media.setLesson(lesson);
        return mediaRepo.save(media);
    }

    public List<Media> getMediaByLesson(Long lessonId)
    {
        return mediaRepo.findByLessonId(lessonId);
    }

    public Media updateMedia(Long mediaId, Media updatedMedia)
    {
        Media media = mediaRepo.findById(mediaId).orElseThrow(() -> new NotFound("Media not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!media.getLesson().getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to update this media");
        }

        media.setFileName(updatedMedia.getFileName());
        media.setFileType(updatedMedia.getFileType());
        media.setFileUrl(updatedMedia.getFileUrl());
        return mediaRepo.save(media);
    }

    public void deleteMedia(Long mediaId)
    {
        Media media = mediaRepo.findById(mediaId).orElseThrow(() -> new NotFound("Media not found"));

        String instructorId = getAuthenticatedInstructorId();
        if (!media.getLesson().getCourse().getInstructor().getId().equals(instructorId) && !isAdmin(instructorId))
        {
            throw new RuntimeException("You are not authorized to delete this media");
        }

        mediaRepo.delete(media);
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
