package ISTP.Dtos.member;


import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AlarmDto {

    private String title;
    private String content;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public AlarmDto(Request request) {
        title = request.getTitle();
        content = request.getContent();
        status = request.getStatus();
        createdTime = request.getCreateDate();
    }
}
