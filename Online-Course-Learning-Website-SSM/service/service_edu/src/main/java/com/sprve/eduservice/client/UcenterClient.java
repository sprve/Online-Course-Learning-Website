package com.sprve.eduservice.client;
import com.sprve.common.utils.commentvo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@FeignClient(name = "service-ucenter",fallback = UcenterFileDegradeFeginClient.class)
@Component
public interface UcenterClient {
    @PostMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberVo getInfoUc(@PathVariable("id") String id);
}
