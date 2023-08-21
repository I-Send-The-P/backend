package ISTP.Dtos.member;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberMyPageDto {

    private String name;
    private Integer age;
    private Gender gender;
    private BloodType myBloodType;
    private String phoneNumber;
    private String nickname;
    private String address;

    public MemberMyPageDto(Member member) {
        name = member.getName();
        age = member.getAge();
        gender = member.getGender();
        myBloodType = member.getMyBloodType();
        phoneNumber = member.getPhoneNumber();
        nickname = member.getNickname();
        address = member.getAddress();
    }
}
