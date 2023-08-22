package ISTP.Dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRe {

    @NotBlank
    private String sickness;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String number;
    @NotBlank
    private String hospital;
    // private BloodType bloodType; // 여기서 라디오버튼 선택 어케하지??
    @NotBlank
    private String relationship;
    @NotBlank
    private String requests_blood_type;
}
