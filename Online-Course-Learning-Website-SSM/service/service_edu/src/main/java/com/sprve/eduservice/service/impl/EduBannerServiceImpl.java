package com.sprve.eduservice.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sprve.eduservice.entity.EduBanner;
import com.sprve.eduservice.mapper.EduBannerMapper;
import com.sprve.eduservice.service.EduBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EduBannerServiceImpl extends ServiceImpl<EduBannerMapper, EduBanner> implements EduBannerService {
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<EduBanner> selectBanner() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByAsc("id");
        List<EduBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
