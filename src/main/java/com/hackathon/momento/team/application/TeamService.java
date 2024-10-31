package com.hackathon.momento.team.application;

import com.hackathon.momento.member.domain.Member;
import com.hackathon.momento.member.domain.repository.MemberRepository;
import com.hackathon.momento.member.exception.MemberNotFoundException;
import com.hackathon.momento.team.api.dto.request.TeamBuildingReqDto;
import com.hackathon.momento.team.domain.repository.TeamBuildingRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamBuildingRepository teamBuildingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTeamBuilding(Principal principal, TeamBuildingReqDto reqDto) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        teamBuildingRepository.save(reqDto.toEntity(member));
    }
}
