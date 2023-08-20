package ISTP.domain.member;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.board.Board;
import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@ToString(of = {"loginId", "password", "name", "age", "gender"})
@NoArgsConstructor
public class Member extends BaseEntity { // 사용자

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private int age;
    @Enumerated(STRING)
    private Gender gender;
    private String phoneNumber;
    @Enumerated(STRING)
    private BloodType myBloodType;

    private String address;
    private LocalDateTime lastDate; // 마지막 헌혈 날짜
    @Enumerated(STRING)
    private Alarm alarm; // 알람기능
    private int count; // 헌혈 횟수

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Accept> accepts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String loginId, String password, String name, String nickname, int age, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    //== 연관관계 메서드==//
    public void addRequest(Request request) {
        requests.add(request);
        request.changeRequest(this);
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.changeQuestion(this);
    }

    public void addAccept(Accept accept) {
        accepts.add(accept);
        accept.changeAccept(this);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.changeAnswer(this);
    }

    public void addBoard(Board board) {
        boards.add(board);
        board.changeBoard(this);
    }
}
