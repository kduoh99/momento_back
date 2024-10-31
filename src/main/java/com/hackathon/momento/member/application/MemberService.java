package com.hackathon.momento.member.application;

import com.hackathon.momento.member.api.dto.request.ProfileReqDto;
import com.hackathon.momento.member.api.dto.request.UpdateProfileReqDto;
import com.hackathon.momento.member.api.dto.response.ProfileResDto;
import com.hackathon.momento.member.domain.Member;
import com.hackathon.momento.member.domain.repository.MemberRepository;
import com.hackathon.momento.member.exception.FirstLoginOnlyException;
import com.hackathon.momento.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void completeProfile(Long memberId, ProfileReqDto reqDto) {
//        Member member = getMemberByPrincipal(principal);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        if (!member.isFirstLogin()) {
            throw new FirstLoginOnlyException();
        }

        member.completeProfile(reqDto.stack(), reqDto.persona(), reqDto.ability());
        memberRepository.save(member);
    }

    public ProfileResDto getProfile(Long memberId) {
//        Member member = getMemberByPrincipal(principal);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return ProfileResDto.from(member);
    }

    @Transactional
    public ProfileResDto updateProfile(Long memberId, UpdateProfileReqDto reqDto) {
//        Member member = getMemberByPrincipal(principal);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        member.updateProfile(reqDto.name(), reqDto.stack(), reqDto.persona(), reqDto.ability());
        return ProfileResDto.from(member);
    }

//    private Member getMemberByPrincipal(Principal principal) {
//        Long memberId = Long.parseLong(principal.getName());
//        return memberRepository.findById(memberId)
//                .orElseThrow(MemberNotFoundException::new);
//    }
}
