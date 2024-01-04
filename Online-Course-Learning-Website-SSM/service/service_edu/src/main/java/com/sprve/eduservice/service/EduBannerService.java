package com.sprve.eduservice.service;
import com.sprve.eduservice.entity.EduBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
public interface EduBannerService extends IService<EduBanner> {
    List<EduBanner> selectBanner();
}
