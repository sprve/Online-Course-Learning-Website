package com.sprve.eduservice.client;
import com.sprve.common.utils.R;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAliyVideo(String id) {
        return R.error().message("删除视频出错了");
    }
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除视频出错了");
    }
}
