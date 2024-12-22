package com.lms.LMS.controllers;

import com.lms.LMS.models.Media;
import com.lms.LMS.services.MediaSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/courses/{lessonId}/media")
@Controller
public class MediaCont
{
    private final MediaSer mediaSer;

    @PostMapping("/create")
    public ResponseEntity<?> addMediaToLesson(@PathVariable Long lessonId, @RequestBody Media media)
    {
        try
        {
            Media savedMedia = mediaSer.addMediaToLesson(lessonId, media);
            return new ResponseEntity<>(savedMedia, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<?> getMediaByLesson(@PathVariable Long lessonId)
    {
        try
        {
            List<Media> mediaList = mediaSer.getMediaByLesson(lessonId);
            return new ResponseEntity<>(mediaList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{mediaId}")
    public ResponseEntity<?> updateMedia(@PathVariable Long lessonId, @PathVariable Long mediaId, @RequestBody Media updatedMedia)
    {
        try
        {
            Media updated = mediaSer.updateMedia(mediaId, updatedMedia);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{mediaId}")
    public ResponseEntity<?> deleteMedia(@PathVariable Long lessonId, @PathVariable Long mediaId)
    {
        try
        {
            mediaSer.deleteMedia(mediaId);
            return ResponseEntity.ok("Media deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
