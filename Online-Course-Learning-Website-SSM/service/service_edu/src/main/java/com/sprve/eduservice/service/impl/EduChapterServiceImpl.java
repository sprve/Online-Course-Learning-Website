package com.sprve.eduservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduservice.entity.EduChapter;
import com.sprve.eduservice.entity.EduVideo;
import com.sprve.eduservice.entity.chapter.ChapterVo;
import com.sprve.eduservice.entity.chapter.VideoVo;
import com.sprve.eduservice.mapper.EduChapterMapper;
import com.sprve.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sprve.eduservice.service.EduVideoService;
import com.sprve.common.base.ExceptionHandler.SprveException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> check(List<ChapterVo> chapterVideoList){
        List<ChapterVo> finallList =chapterVideoList;
        ChapterVo chapterVo;
        List<VideoVo> listVideoVo;
        VideoVo videoVo;
        for(int i=0;i<chapterVideoList.size();i++){
            chapterVo=chapterVideoList.get(i);
            listVideoVo=chapterVo.getChildren();
            for(int j=0;j<listVideoVo.size();j++){
                videoVo=listVideoVo.get(j);
                QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
                wrapper.eq("video_source_id",videoVo.getVideoSourceId());
                wrapper.eq("id",videoVo.getId());
                if(videoService.getOne(wrapper).getIsFree().intValue()==0){
                    videoVo.setVideoSourceId(null);
                };
            }
        }
        return finallList;
    }
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        List<ChapterVo> finallList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finallList.add(chapterVo);
            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        return finallList;
    }
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count > 0) {
            throw new SprveException(40000,"不能删除");
        }else{
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
