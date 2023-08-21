package ISTP.Dtos.member;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveForm {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String rePassword;

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotNull
    private BloodType bloodType;

    @NotBlank
    private String nickname;

}
