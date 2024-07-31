package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(MemberDTO memberDTO) {
        memberDTO.setMemberPassword(passwordEncoder.encode(memberDTO.getMemberPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO) {
        return memberRepository.findByMemberEmail(memberDTO.getMemberEmail())
                .filter(memberEntity -> passwordEncoder.matches(memberDTO.getMemberPassword(), memberEntity.getMemberPassword()))
                .map(MemberDTO::toMemberDTO)
                .orElse(null);
    }

    public MemberDTO findByEmail(String email) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(email);
        return optionalMember.map(MemberDTO::toMemberDTO).orElse(null);
    }

    public List<MemberDTO> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberDTO::toMemberDTO)
                .collect(Collectors.toList());
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(id);
        return optionalMember.map(MemberDTO::toMemberDTO).orElse(null);
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberEmail(myEmail);
        return optionalMember.map(MemberDTO::toMemberDTO).orElse(null);
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        boolean exists = memberRepository.findByMemberEmail(memberEmail).isPresent();
        return exists ? "exist" : "ok";
    }
}








