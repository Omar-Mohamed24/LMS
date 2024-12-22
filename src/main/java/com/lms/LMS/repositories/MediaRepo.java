package com.lms.LMS.repositories;

import com.lms.LMS.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaRepo extends JpaRepository<Media, Long>
{
    List<Media> findByLessonId(Long lessonId);
}
