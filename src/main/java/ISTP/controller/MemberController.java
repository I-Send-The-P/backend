package ISTP.controller;

import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/save")
    public void save(@RequestBody String loginId) {
        Member abc = new Member(loginId, "abc");
        Long id = memberService.save(abc);
    }

}
