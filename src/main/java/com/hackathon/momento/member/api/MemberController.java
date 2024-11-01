package com.hackathon.momento.member.api;

import com.hackathon.momento.global.template.RspTemplate;
import com.hackathon.momento.member.api.dto.request.ProfileReqDto;
import com.hackathon.momento.member.api.dto.request.UpdateProfileReqDto;
import com.hackathon.momento.member.api.dto.response.ProfileResDto;
import com.hackathon.momento.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "사용자 정보", description = "사용자 정보를 담당하는 API 그룹")
public class MemberController {

    private static final Long TEMP_MEMBER_ID = 1L; // 테스트용 임시 사용자 ID

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
    public RspTemplate<Void> completeProfile(
            @Valid @RequestBody ProfileReqDto reqDto) {

        memberService.completeProfile(TEMP_MEMBER_ID, reqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "프로필 완성 성공");
    }

    @GetMapping("/profile")
    @Operation(
            summary = "프로필 조회",
            description = "현재 로그인한 사용자의 프로필 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public RspTemplate<ProfileResDto> getProfile() {
        ProfileResDto profile = memberService.getProfile(TEMP_MEMBER_ID);
        return new RspTemplate<>(HttpStatus.OK, "프로필 조회 성공", profile);
    }

    @PatchMapping("/update-profile")
    @Operation(
            summary = "프로필 수정",
            description = "현재 로그인한 사용자의 프로필 정보를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "프로필 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public RspTemplate<ProfileResDto> updateProfile(
            @Valid @RequestBody UpdateProfileReqDto reqDto) {

        ProfileResDto updatedProfile = memberService.updateProfile(TEMP_MEMBER_ID, reqDto);
        return new RspTemplate<>(HttpStatus.OK, "프로필 수정 성공", updatedProfile);
    }
}
