package ISTP.repository;

import ISTP.domain.bloodDonation.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
