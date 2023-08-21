package ISTP.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Requestrequest {

    private String sickness;
    private String title;
    private String content;
    private String number;
    private String hospital;
    // private BloodType bloodType;
    private String relationship;
    private String requests_blood_type;
}
