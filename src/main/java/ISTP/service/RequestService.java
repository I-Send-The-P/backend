package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    @Transactional
    public Long save(Request request) {
        Request saveRequest = requestRepository.save(request);
        return saveRequest.getId();
    }

    public Request findById(Long requestId) {
        return requestRepository.findById(requestId).
                orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Transactional
    public void changeStatus(Request request) { // 신청에서 진행중으로 바꾸기
        request.update_status();
    }

    @Transactional
    public void changeStatus2(Request request) { // 진행중에서 완료로 바꾸기
        request.update_finish();
    }

    @Transactional
    public void changeStatus3(Request request) { // 취소누르면 다시 신청으로 바꾸기
        request.update_request();
    }

    @Transactional
    public void delete(Long requestId) {
        requestRepository.deleteById(requestId);
    }

    //알람 발송을 위해 혈액형이 같은 모든 멤버 조회 메서드
    public List<Request> findAllByBloodType(BloodType bloodType) {
        return requestRepository.findAllByBloodType(bloodType);
    }

}
