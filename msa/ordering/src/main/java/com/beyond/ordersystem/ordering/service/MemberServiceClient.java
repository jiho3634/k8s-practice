package com.beyond.ordersystem.ordering.service;

import com.beyond.ordersystem.common.dto.CommonResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service", configuration = FeignClientConfig.class) // FeignClientConfig 설정 적용
public interface MemberServiceClient {

    @GetMapping("/member/myinfo")
    ResponseEntity<CommonResDto> getMemberInfo();
}
