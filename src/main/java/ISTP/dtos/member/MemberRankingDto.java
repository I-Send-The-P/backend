package ISTP.dtos.member;

import ISTP.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberRankingDto {

    private String nickname;
    private int count;

    public MemberRankingDto(Member member) {
        nickname = member.getNickname();
        count = member.getCount();
    }
}
