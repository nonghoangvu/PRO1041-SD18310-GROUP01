package udpm.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udpm.fpt.model.UserDetails;

/**
 *
 * @author BinhQuoc
 */
public interface IUserDetails extends JpaRepository<UserDetails, Integer>{
    
}
