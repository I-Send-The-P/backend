package ISTP.domain.help.question;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.Answer;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@ToString(of = {"id", "title", "content", "inquiryType", "status"})
@NoArgsConstructor
public class Question extends BaseEntity { // 문의사항

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;
    private String content;

    @Enumerated(STRING)
    @Column(name = "inquiry_status")
    private InquiryStatus status; //문의 상태

    @Enumerated(STRING)
    private InquiryType inquiryType; //문의 유형
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Question(String title, String content, InquiryType inquiryType, Member member) {
        this.title = title;
        this.content = content;
        this.status = InquiryStatus.문의중;
        this.inquiryType = inquiryType;
        if(member != null) {
            changeQuestion(member);
        }
    }



    //==연관관계 메서드==//
    public void changeQuestion(Member member) {
        this.member = member;
        member.addQuestion(this); //연관 관계설정
    }

    //답변을 받으면 문의중 -> 문의 상태로 바꾸는 메서드
    public void changeStatus() {
        this.status = InquiryStatus.문의완료;
    }


    //문의글 수정하여 업데이트하는 메서드
    public void updateQuestion(String title, String content, InquiryType inquiryType) {
        this.title = title;
        this.content = content;
        this.inquiryType = inquiryType;
    }
}
