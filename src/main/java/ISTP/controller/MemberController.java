package ISTP.controller;

import ISTP.Dtos.member.MemberSaveForm;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.dtos.member.MemberMyPageDto;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.dtos.request.MyAcceptDto;
import ISTP.dtos.request.MyRequestDto;
import ISTP.service.AcceptService;
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
    private final AcceptService acceptService;

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

    /**
     * 수정시 닉네임과 관련된 모든 부분 고쳐야하는데 아직 안했음!! 
     */
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
    @DeleteMapping("/myPages/{memberId}/delete")
    public void deleteMember(@PathVariable Long memberId){
        Member member = memberService.findById(memberId);
        memberService.withdrawal(member);
    }

    //내가 등록한 긴급헌혈 요청서 목록
    @ResponseBody
    @GetMapping("/maPages/{memberId}/myRequests")
    public List<MyRequestDto> myRequestList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        List<Request> allByMemberNickname = requestService.findAllByMemberNickname(member.getNickname());
        List<MyRequestDto> requestDtos = new ArrayList<>();
        for (Request request : allByMemberNickname) {
            MyRequestDto requestDto = new MyRequestDto(request);
            requestDtos.add(requestDto);
        }
        return requestDtos;
    }

    //내가 요청 수락을 한 긴급헌혈 요청서 목록
    @ResponseBody
    @GetMapping("/myPages/{memberId}/myAccepts")
    public List<MyAcceptDto> myAcceptRequestList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        List<Accept> accepts = acceptService.findByMember(member);
        List<MyAcceptDto> acceptDtos = new ArrayList<>();
        for (Accept accept : accepts) {
            Request request = accept.getRequest();
            MyAcceptDto acceptDto = new MyAcceptDto(request);
            acceptDtos.add(acceptDto);
        }
        return acceptDtos;
    }
}
