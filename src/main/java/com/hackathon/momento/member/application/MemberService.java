package com.hackathon.momento.member.application;

import com.hackathon.momento.member.api.dto.request.ProfileReqDto;
import com.hackathon.momento.member.api.dto.request.UpdateProfileReqDto;
import com.hackathon.momento.member.api.dto.response.ProfileResDto;
import com.hackathon.momento.member.domain.Member;
import com.hackathon.momento.member.domain.repository.MemberRepository;
import com.hackathon.momento.member.exception.FirstLoginOnlyException;
import com.hackathon.momento.member.exception.MemberNotFoundException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void completeProfile(Principal principal, ProfileReqDto reqDto) {
        Member member = getMemberByPrincipal(principal);

        if (!member.isFirstLogin()) {
            throw new FirstLoginOnlyException();
        }

        member.completeProfile(reqDto.stack(), reqDto.persona(), reqDto.ability());
        memberRepository.save(member);
    }

    public ProfileResDto getProfile(Principal principal) {
        Member member = getMemberByPrincipal(principal);

        return ProfileResDto.from(member);
    }

    @Transactional
    public ProfileResDto updateProfile(Principal principal, UpdateProfileReqDto reqDto) {
        Member member = getMemberByPrincipal(principal);
        member.updateProfile(reqDto.name(), reqDto.stack(), reqDto.persona(), reqDto.ability());

        return ProfileResDto.from(member);
    }

    private Member getMemberByPrincipal(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
