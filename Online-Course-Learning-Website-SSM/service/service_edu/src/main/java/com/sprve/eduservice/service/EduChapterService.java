package com.sprve.eduservice.service;
import com.sprve.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sprve.eduservice.entity.chapter.ChapterVo;
import java.util.List;
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);
    List<ChapterVo> check(List<ChapterVo> chapterVideoList);
    boolean deleteChapter(String chapterId);
    void removeChapterByCourseId(String courseId);
}
