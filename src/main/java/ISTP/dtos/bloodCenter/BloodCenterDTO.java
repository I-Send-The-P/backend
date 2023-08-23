package ISTP.dtos.bloodCenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodCenterDTO {
    private String phoneNumber;
    private String address;
    private String DonationCenter;
}