package sobar.app.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sobar.app.service.model.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {

}
