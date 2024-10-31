package com.hackathon.momento.team.api;

import com.hackathon.momento.global.template.RspTemplate;
import com.hackathon.momento.team.api.dto.request.TeamBuildingReqDto;
import com.hackathon.momento.team.application.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team")
@Tag(name = "팀", description = "팀을 담당하는 API 그룹")
public class TeamController {

    private static final Long TEMP_MEMBER_ID = 1L; // 테스트용 임시 사용자 ID

    private final TeamService teamService;

    @PostMapping("/building")
    @Operation(
            summary = "팀 빌딩 정보 저장",
            description = "사용자가 팀 빌딩을 위해 필요한 정보들을 입력합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "팀 빌딩 정보 저장 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 오류")
            }
    )
    public RspTemplate<Void> saveTeamBuilding(
//            Principal principal,
            @Valid @RequestBody TeamBuildingReqDto reqDto) {

        teamService.saveTeamBuilding(TEMP_MEMBER_ID, reqDto);
        return new RspTemplate<>(HttpStatus.OK, "팀 빌딩 정보 저장 성공");
    }
}
