package com.hackathon.momento.team.application;

import com.hackathon.momento.member.domain.Member;
import com.hackathon.momento.member.domain.repository.MemberRepository;
import com.hackathon.momento.member.exception.MemberNotFoundException;
import com.hackathon.momento.team.api.dto.request.TeamBuildingReqDto;
import com.hackathon.momento.team.domain.Status;
import com.hackathon.momento.team.domain.repository.TeamBuildingRepository;
import com.hackathon.momento.team.exception.TeamBuildingConflictException;
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
    public void saveTeamBuilding(Long memberId, TeamBuildingReqDto reqDto) {
//        Long memberId = Long.parseLong(principal.getName());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        if (teamBuildingRepository.existsByMemberAndStatus(member, Status.PENDING)) {
            throw new TeamBuildingConflictException();
        }

        teamBuildingRepository.save(reqDto.toEntity(member));
    }
}
