package ISTP.controller;

import ISTP.Dtos.member.MemberSaveForm;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/save")
    public Long save(@Validated @RequestBody MemberSaveForm form, BindingResult bindingResult) {

        if (!(memberService.passwordReEnter(form.getPassword(), form.getRePassword()))) {
            bindingResult.reject("passwordMismatch", null);
        }

        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //이러고 어떻게 할지 고민 중
            throw new IllegalArgumentException("회원가입 오류발생");

        }

        Member member = new Member(form.getLoginId(), form.getPassword(),
                form.getName(), form.getNickname(), form.getAge(), form.getGender(),
                form.getPhoneNumber(), form.getBloodType(), form.getEmail(), form.getAddress());

        Long memberId = memberService.save(member);
        return memberId;
    }
}
