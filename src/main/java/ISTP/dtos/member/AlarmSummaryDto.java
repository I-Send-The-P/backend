package ISTP.dtos.member;


import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AlarmSummaryDto {

    private String title;
    private String content;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public AlarmSummaryDto(Request request) {
        title = request.getTitle();
        content = request.getContent();
        status = request.getStatus();
        createdTime = request.getCreateDate();
    }
}
