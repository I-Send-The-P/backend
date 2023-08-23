package ISTP.controller;

import ISTP.domain.member.Member;
import ISTP.dtos.member.MemberRankingDto;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final MemberService memberService;

    //랭킹보여줄 DTO
    @ResponseBody
    @GetMapping
    public List<MemberRankingDto> ranking() {
        List<Member> top5ByCount = memberService.findTop5ByCount();
        List<MemberRankingDto> memberRankingDtos = new ArrayList<>();
        for (Member member : top5ByCount) {
            MemberRankingDto memberRankingDto = new MemberRankingDto(member);
            memberRankingDtos.add(memberRankingDto);
        }
        return memberRankingDtos;
    }

    @GetMapping("banner")
    public String banner() {
        return "https://istp.s3.ap-northeast-2.amazonaws.com/1.jfif";
    }
}
