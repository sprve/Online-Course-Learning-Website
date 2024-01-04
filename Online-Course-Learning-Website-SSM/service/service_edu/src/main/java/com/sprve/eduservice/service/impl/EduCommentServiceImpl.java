package com.sprve.eduservice.service.impl;
import com.sprve.eduservice.entity.EduComment;
import com.sprve.eduservice.mapper.EduCommentMapper;
import com.sprve.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
}
