package ISTP.controller;

import ISTP.Dtos.member.AlarmDto;
import ISTP.Dtos.member.MemberMyPageDto;
import ISTP.Dtos.member.MemberSaveForm;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final RequestService requestService;

    //회원가입 로직
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

    //회원가입 시 로그인 아이디 중복 확인 로직
    @PostMapping("/save/check_duplicate/loginId")
    @ResponseBody
    public String checkDuplicateLoginId(@RequestParam String loginId) {
         if(memberService.duplicatedLoginId(loginId)) {
             return "ok";
         }
         else {
             return "no";
         }
    }

    //회원가입 시 닉네임 중복 확인 로직
    @PostMapping("/save/check_duplicate/nickname")
    @ResponseBody
    public String checkDuplicateNickname(@RequestParam String nickname) {
        if(memberService.duplicatedNickname(nickname)) {
            return "ok";
        }
        else {
            return "no";
        }
    }

    //마이페이지로에 뿌려줄 DTO
    @ResponseBody
    @GetMapping("/myPages/{memberId}")
    public MemberMyPageDto myPage(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        MemberMyPageDto myPageDto = new MemberMyPageDto(member);
        return myPageDto;
    }

    //닉네임 수정
    @PostMapping("/myPages/{memberId}/edit/nickname")
    public void editNickName(@PathVariable Long memberId, @RequestParam String nickname) {
        Member member = memberService.findById(memberId);
        memberService.changeNickname(member, nickname);
    }

    //주소 수정
    @PostMapping("/myPages/{memberId}/edit/address")
    public void editAddress(@PathVariable Long memberId, @RequestParam String address) {
        Member member = memberService.findById(memberId);
        memberService.changeAddress(member, address);
    }

    //회원 삭제
    @DeleteMapping("/maPages/{memberId}/delete")
    public void deleteMember(@PathVariable Long memberId){
        Member member = memberService.findById(memberId);
        memberService.withdrawal(member);
    }


    @ResponseBody
    @GetMapping("myAlarms/{memberId}")
    public List<AlarmDto> myAlarm(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        List<Request> allByBloodType = requestService.findAllByBloodType(member.getMyBloodType());
        List<AlarmDto> alarmDtoList = new ArrayList<>();
        for (Request request : allByBloodType) {
            AlarmDto alarmDto = new AlarmDto(request);
            alarmDtoList.add(alarmDto);
        }
        return alarmDtoList;
    }


}
