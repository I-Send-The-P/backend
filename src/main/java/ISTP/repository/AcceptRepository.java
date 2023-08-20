package ISTP.repository;

import ISTP.domain.bloodDonation.accept.Accept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptRepository extends JpaRepository<Accept, Long> {
}
