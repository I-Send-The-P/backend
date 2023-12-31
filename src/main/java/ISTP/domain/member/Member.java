package ISTP.domain.member;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.BloodTypeCategories;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.board.Board;
import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Member extends BaseEntity { // 사용자

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private Integer age; //생년월일이어야할듯
    private boolean gender; // true 면 남자, false 면 여자
    private String phoneNumber;
    @Column(name = "my_blood_type_id")
    private Long myBloodTypeId;
    private String email; //이메일이 없었음 ㅜㅜ
    private String address;
    private boolean alarmStatus; // 알람기능
    private LocalDateTime lastDate; // 마지막 헌혈 날짜
    private int count = 0; // 헌혈 횟수

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


    public Member() {
    }


    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String loginId, String password, String name, String nickname, Integer age, String phoneNumber, boolean alarmStatus) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.alarmStatus = true;
        this.alarmStatus = alarmStatus;
    }

    public Member(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.alarmStatus = true;
        this.alarmStatus = true;
    }

    public Member(String loginId, String password, String nickname, String address) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.alarmStatus = true;
        this.alarmStatus = true;
    }

    public Member(String loginId, String password, String name, String nickname, Integer age, boolean gender, String phoneNumber, Long myBloodType, String email, String address, boolean alarmStatus) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.myBloodTypeId = myBloodType;
        this.email = email;
        this.address = address;
        this.alarmStatus = alarmStatus;
    }

    //== 연관관계 메서드==//
    public void addRequest(Request request) {
        requests.add(request);
        request.changeRequest(this);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void addAccept(Accept accept) {
        accepts.add(accept);
        accept.changeAccept(this);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    // 닉네임 변경하는 메서드
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    //주소 변경하는 메서드
    public void changeAddress(String address) {
        this.address = address;
    }

    //알람 상태 변경하는 메서드
    public void changeAlarm() {
        if (alarmStatus) {
            alarmStatus = false;
        } else {
            alarmStatus = true;
        }
    }

    public void update(String phoneNumber, String nickname, String address) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.address = address;
    }

    //헌혈 완료하면 호출하여 헌혈 횟수 증가시키기
    public void countPlus() {
        count++;
    }

    public void setMyBloodType(BloodTypeCategories myBloodType) {
        this.myBloodTypeId = myBloodType.getId();
    }
}
