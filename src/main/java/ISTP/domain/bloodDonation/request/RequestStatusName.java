package ISTP.domain.bloodDonation.request;

public interface RequestStatusName {
    String APPLICATION = "요청신청";
    Long APPLICATION_ID = 1L;
    String PROGRESS = "요청진행";
    Long PROGRESS_ID = 2L;
    String COMPLETED = "요청완료";
    Long COMPLETED_ID = 3L;


}