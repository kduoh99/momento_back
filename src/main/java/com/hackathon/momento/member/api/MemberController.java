package com.hackathon.momento.member.api;

import com.hackathon.momento.global.template.RspTemplate;
import com.hackathon.momento.member.api.dto.request.ProfileReqDto;
import com.hackathon.momento.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "사용자 정보", description = "사용자 정보를 담당하는 API 그룹")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/complete-profile")
    @Operation(
            summary = "최초 로그인 시 프로필 완성",
            description = "최초 로그인 시 스택, 성격, 역량 정보를 입력받아 프로필을 완성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 완성 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public RspTemplate<String> completeProfile(
            Principal principal,
            @Valid @RequestBody ProfileReqDto reqDto) {

        memberService.completeProfile(principal, reqDto);
        return new RspTemplate<>(HttpStatus.OK, "프로필이 성공적으로 완성되었습니다.");
    }
}
