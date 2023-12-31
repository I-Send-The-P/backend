package ISTP.controller;

import ISTP.dtos.login.LoginDtoRequest;
import ISTP.dtos.member.MemberChangeDto;
import ISTP.dtos.member.MemberSaveForm;
import ISTP.dtos.member.MemberMyPageDto;
import ISTP.domain.member.Member;
import ISTP.login.LoginService;
import ISTP.login.SessionConst;
import ISTP.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final HttpSession session;

    //회원가입 로직
    @PostMapping
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
                form.getName(), form.getNickname(), form.getAge(), form.isGender(),
                form.getPhoneNumber(), form.getBloodType(), form.getEmail(), form.getAddress(), true);

        Long memberId = memberService.save(member);
        return memberId;
    }

    //회원가입 시 로그인 아이디 중복 확인 로직
    @PostMapping("/duplicate/loginId")
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
    @PostMapping("duplicate/nickname")
    @ResponseBody
    public String checkDuplicateNickname(@RequestParam String nickname) {
        if(memberService.duplicatedNickname(nickname)) {
            return "ok";
        }
        else {
            return "no";
        }
    }

    //회원가입 시 닉네임 중복 확인 로직
    @PostMapping("/duplicate/phoneNumber")
    @ResponseBody
    public String checkDuplicatePhoneNumber(@RequestParam String phoneNumber) {
        if(memberService.duplicatedPhoneNumber(phoneNumber)) {
            return "ok";
        }
        else {
            return "no";
        }
    }

    //마이페이지에 뿌려줄 DTO
    @ResponseBody
    @GetMapping
    public MemberMyPageDto myPage() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        MemberMyPageDto myPageDto = new MemberMyPageDto(member);
        return myPageDto;
    }

    @PostMapping("/login")
    public ResponseEntity login (@Validated @RequestBody LoginDtoRequest request, BindingResult bindingResult,
                                 HttpServletRequest httpServletRequest) {
        /*Member byLoginId = memberService.findByLoginId(request.getLoginId());
        Long id = byLoginId.getId();
        if(bindingResult.hasErrors()) {
            log.info("로그인 오류");
            return null;
            //return new ResponseEntity(HttpStatus.BAD_REQUEST);
            // 로그인 페이지로 리다이렉트
        }

        Member loginMember = loginService.login(request.getLoginId(), request.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail");
            return null;
            //return new ResponseEntity(HttpStatus.BAD_REQUEST); // 여기도 로그인 실패로 리다이렉트
        }

        HttpSession session = httpServletRequest.getSession();
        // request.getSession() -> 세션이 있으면 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        // 세션에 로그인 회원 정보를 보관

        return id;*/

        if(bindingResult.hasErrors()) {
            log.info("로그인 오류");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
            // 로그인 페이지로 리다이렉트
        }

        Member loginMember = loginService.login(request.getLoginId(), request.getPassword());

        if(loginMember == null) {
            bindingResult.reject("loginFail");
            return new ResponseEntity(HttpStatus.BAD_REQUEST); // 여기도 로그인 실패로 리다이렉트
        }

        HttpSession session = httpServletRequest.getSession();
        // request.getSession() -> 세션이 있으면 세션 반환, 없으면 신규 세션을 생성
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        // 세션에 로그인 회원 정보를 보관

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity(HttpStatus.OK); // 홈 페이지로 리다이렉트
    }


    @PutMapping
    public ResponseEntity<?> change(@RequestBody MemberChangeDto memberChangeDto) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        ResponseEntity<?> responseEntity = memberService.changeMember(member, memberChangeDto.getPhoneNumber(), memberChangeDto.getUserNickname(),
                memberChangeDto.getAddress());
        return responseEntity;
    }

    /*//닉네임 수정
    @PutMapping("/myPages/{memberId}/edit/nickname")
    public void editNickName(@PathVariable Long memberId, @RequestParam String nickname) {
        Member member = memberService.findById(memberId);
        memberService.changeNickname(member, nickname);
    }

    //주소 수정
    @PutMapping("/myPages/{memberId}/edit/address")
    public void editAddress(@PathVariable Long memberId, @RequestParam String address) {
        Member member = memberService.findById(memberId);
        memberService.changeAddress(member, address);
    }
*/



}
